from flask_sqlalchemy import SQLAlchemy
db = SQLAlchemy()

"""This is the database interaction module. Classes contained within represent
instances of groups, restaurants, or users."""


class User(db.Model):
    __tablename__ = "users"
    id = db.Column(db.Integer, primary_key=True)
    user = db.Column(db.String, nullable=False)
    hash_pw = db.Column(db.String, nullable=False)


class Friends(db.Model):
    __tablename__ = "friends"
    id = db.Column(db.Integer, primary_key=True)
    inviter = db.Column(db.String, nullable=False)
    invitee = db.Column(db.String, nullable=False)
    #Accepted = False
    accepted = db.Column(db.Integer, nullable=False)


class Tags(db.Model):
    __tablename__ = "tags"
    """Tags for categories of food, shared in survey and by restaurants"""
    id = db.Column(db.Integer, primary_key=True)
    # Category is res for restaurants and grp for groups
    category = db.Column(db.String, nullable=False, default="res")
    name = db.Column(db.Integer, nullable=False)
    tag = db.Column(db.Integer, nullable=False)


class Ingredients(db.Model):
    __tablename__ = "ingredients"
    id = db.Column(db.Integer, primary_key=True)
    ingredient = db.Column(db.String, nullable=False)


class Restaurants(db.Model):
    __tablename__ = "restaurants"
    res_id = db.Column(db.Integer, primary_key=True)
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
    loc_X = db.Column(db.Integer, nullable=False)
    loc_Y = db.Column(db.Integer, nullable=False)
