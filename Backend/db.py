from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()

"""This is the database interaction module. Classes contained within represent
instances of groups, restaurants, or users."""


class User(db.Model):
    __tablename__ = "users"
    id = db.Column(db.Integer, primary_key=True)
    user = db.Column(db.String, nullable=False, unique = True)
    hash_pw = db.Column(db.String, nullable=False)

    def serialize_user(self):
        return {"id": self.id, "username": self.user, "hash": self.hash_pw}


class Friends(db.Model):
    __tablename__ = "friends"
    id = db.Column(db.Integer, primary_key=True)
    inviter = db.Column(db.Integer, nullable=False)
    invitee = db.Column(db.Integer, nullable=False)
    #Accepted = 0
    accepted = db.Column(db.Integer, nullable=False, default = 0)

    def serialize_friendship(self):
        return {"id": self.id, 
        "f1": self.inviter, 
        "f2": self.invitee, 
        "accepted": self.accepted}


class Restaurants(db.Model):
    __tablename__ = "restaurants"
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String, nullable=False)
    price = db.Column(db.Integer, nullable=False)
    image = db.Column(db.String, nullable=False)
    # Scale of 1-5
    rating = db.Column(db.Integer, nullable=False, default=5)
    description = db.Column(db.String)
    # Scale of 1-3
    wait_time = db.Column(db.Integer)
    phone = db.Column(db.String)
    # locations are geographic coordinates
    loc_x = db.Column(db.Integer, nullable=False)
    loc_y = db.Column(db.Integer, nullable=False)

    def serialize_rest(self):
        return{"id": self.id, 
            "name": self.name,
            "price": self.price,
            "image": self.image,
            "rating": self.rating,
            "description": self.description,
            "wait_time": self.wait_time,
            "phone": self.phone,
            "location_x": self.loc_x,
            "location_y": self.loc_y}


class Tags(db.Model):
    __tablename__ = "tags"
    __table_args__ = (db.UniqueConstraint('category', 'name', 'tag'),)
    """Tags for categories of food, shared in survey and by restaurants"""
    id = db.Column(db.Integer, primary_key=True)
    # Category is res for restaurants and grp for groups
    category = db.Column(db.String, nullable=False, default="res")
    name = db.Column(db.Integer, nullable=False)
    tag = db.Column(db.Integer, nullable=False)

    def serialize_tag(self):
        return {"id": self.id, 
        "category": self.category, 
        "name": self.name,
        "tag": self.tag}


"""
class Ingredients(db.Model):
    __tablename__ = "ingredients"
    id = db.Column(db.Integer, primary_key=True)
    ingredient = db.Column(db.String, nullable=False)


class Restrictions(db.Model):
    People are category ppl, restaurants are res, and groups are grp
    __tablename__ = "restrictions"
    id = db.Column(db.Integer, primary_key=True)
    category = db.Column(db.String, nullable=False, default="ppl")
    name = db.Column(db.String, nullable=False)
    ingr_id = db.Column(db.Integer, nullable=False)"""


#Groups
class Group(db.Model):
    __tablename__ = "groups"
    id = db.Column(db.Integer, primary_key=True)
    host = db.Column(db.Integer, nullable = False)
    name = db.Column(db.String, nullable = False)
    date = db.Column(db.String, nullable=False)
    num_members = db.Column(db.Integer, default=0)
    tot_x = db.Column(db.Float, default=0)
    tot_y = db.Column(db.Float, default=0)
    tot_price = db.Column(db.Integer, nullable=False, default=0)
    #distance in miles
    tot_dist = db.Column(db.Integer, nullable=False, default=0)
    tot_time = db.Column(db.Integer, nullable=False, default=0)
    pick = db.Column(db.Integer, nullable=True)

    def serialize_group(self):
        return {"group_id":self.id,
        "date": self.date,
        "host": self.host,
        "name": self.name,
        "members": [],
        "survey_complete": False,
        "top_choices": [],
        "voting_complete": False,
        "final_choice": self.pick}
    
    def serialize_internal(self):
        return {"group_id": self.id,
        "num_members": self.num_members,
        "price": self.tot_price / self.num_members,
        "dist": self.tot_dist / self.num_members,
        "time": self.tot_time / self.num_members,
        "ctr_x": self.tot_x / self.num_members,
        "ctr_y": self.tot_y / self.num_members
        }


class GroupMembers(db.Model):
    __tablename__ = "invitations"
    id = db.Column(db.Integer, primary_key=True)
    user = db.Column(db.Integer, nullable=False)
    group = db.Column(db.Integer, nullable=False)
    accepted = db.Column(db.Integer, nullable=False, default=0)
    ready = db.Column(db.Integer, nullable=False, default=0)

    def serialize_group_mem(self):
        return {"id": self.id, 
        "user": self.user, 
        "group": self.group, 
        "accepted": bool(self.accepted)}

# Voting
class TopChoices(db.Model):
    __tablename__ = "top_choices"
    id = db.Column(db.Integer, primary_key=True)
    group = db.Column(db.Integer, nullable=False)
    res = db.Column(db.Integer, nullable=False)
    rating = db.Column(db.Integer, nullable=False)

    def serialize_top(self):
        return {"id": self.id,
        "group": self.group,
        "res": self.res,
        "rating": self.rating}

class BordaVote(db.Model):
    __tablename__ = "borda_vote"
    id = db.Column(db.Integer, primary_key=True)
    group = db.Column(db.Integer)
    # Ranking from 0-4, with 0 being most preferred and 4 the least
    rank = db.Column(db.Integer, nullable=False)
    restaurant = db.Column(db.Integer, nullable=False)
    def serialize_borda(self):
        return {"id": self.id, 
        "group": self.group, 
        "rank": self.rank,
        "restaurant": self.restaurant}
