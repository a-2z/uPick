from db import *
from exceptions import *
from math import sqrt
from consts import NUM_CHOICES

"""Database interaction methods"""

def get_user(user_id):
    """Get a user's username and friends by id"""
    un = User.query.filter_by(id=user_id).first()
    friends = []
    friend_list = db.session.query(Friends).filter(((Friends.inviter == user_id) 
    | (Friends.invitee == user_id)) & (Friends.accepted == 1)).all()
    for friend in friend_list:
        f_ship = friend.serialize_friendship()
        if f_ship["f1"] == user_id:
            friends.append (f_ship["f2"])
        else:
            friends.append(f_ship["f1"])
    if un is None:
        raise UserNotFound(user_id)
    else:
        username = un.serialize_user()["username"]
        return {"id": user_id, "name": username, "friends": friends}

def get_pending(user_id):
    """Get sent or recieved friend requests that are not accepted"""
    friends = []
    friend_list = db.session.query(Friends).filter(((Friends.inviter == user_id) 
    | (Friends.invitee == user_id)) & (Friends.accepted == 0)).all()
    sent = []
    received = []
    for friend in friend_list:
        f_ship = friend.serialize_friendship()
        if f_ship["f1"] == user_id:
            sent.append (f_ship["f2"])
        else:
            received.append(f_ship["f1"])
    return {"sent": sent, "received": received}

def get_group(group):
    group_row = Group.query.filter_by(id=group).first()
    grp = group_row.serialize_group()
    mem = []
    for m in GroupMembers.filter_by(group=group,accepted=1).all():
        mem.append(m.serialize_group_mem()["user"])
    grp["members"] = mem
    num_ready = GroupMembers.filter_by(group=group,ready=1).count()
    if num_ready == len(mem) and not grp["survey_complete"]:
        grp["survey_complete"] = True
        tc = filter_restaurants(group)
        for t in tc:
            choice = TopChoices(group=t["group"],res=t["res"],rating=t["rating"])
            db.session.add(choice)
            db.session.commit()
    #Compute voting 
    votes = BordaVote.query.filter_by(group=group).all()
    if len(mem)*NUM_CHOICES == len(votes) and not grp["voting_complete"]:
        grp["voting_complete"] = True
        tally = {}
        for v in votes:
            res_str = str(v.restaurant)
            if res_str not in tally.keys():
                tally[res_str] = v.rank
            else:
                tally[res_str] += v.rank 
        grp["final_choice"] = int(next(key for key, value in d.iteritems() 
        if value == max(tally.values())))
        group_row.pick = grp["final_choice"]
        db.session.commit()
    return grp
        
def filter_restaurants(group):
    aggregate_prefs = Group.query.filter_by(id=group).first().serialize_internal()
    group_tags = []
    for t in Tags.query.filter_by(category="grp",name=group).all():
        group_tags.append (t.tag)
    top = []
    for r in Restaurants.query.filter(
        (Restaurants.price <= aggregate_prefs["price"]) &
        (Restaurants.wait_time <= aggregate_prefs["time"]) &
        #check location is within radius of center
        (
            sqrt((aggregate_prefs["ctr_x"] - Restaurants.loc_x)**2 + 
            (aggregate_prefs["ctr_y"] - Restaurants.loc_y)**2) <= 
            aggregate_prefs["dist"]) &
        #check that at least one tag is present
        (any(map(lambda x: x.tag in group_tags,
        Tags.query.filter_by(category="res", name=Restaurants.id).all()))
        )).order_by(desc(Restaurants.rating)).limit(NUM_CHOICES).all():
        top.append({"group":group, "res": r.id, "rating": r.rating})
    return top

def get_invites(user):
    invs = GroupMembers.query.filter_by(user=user,accepted=0).all()
    invitations = []
    for i in invs:
        invitations.append(i.serialize_group_mem()["group"])
    return {"invitations": invitations}
    
def get_restaurant(rest_id):
    return Restaurants.query.filter_by(id=rest_id).first().serialize_rest()

def create_user(user, hash):
    """Store a new user's username and hash"""
    try:
        new_user = User(user = user, hash_pw = hash)
        db.session.add(new_user)
        db.session.commit()
        return User.query.filter_by(user=user).first().serialize_user()["id"]
    except:
        raise UserExists(user)


def authenticate(user, password):
    """return a user's hash"""
    user_pass = User.query.filter_by(user=user).first()
    if user_pass is None:
        raise UserNotFound(user)
    else:
        return user_pass.serialize_user()["hash"]

def add_friend(f1, f2):
    friendship = Friends(inviter=f1, invitee=f2)
    f_ship = Friends.query.filter_by(
        (((Restaurants.inviter = f1) & Restaurants.invitee == f2) & 
        (accepted == 0)) |
        (((Restaurants.inviter = f2) & Restaurants.invitee == f1) & 
        (accepted == 0))).first()
    if f_ship is None:
        db.session.add(friendship)
    else:
        f_ship.accepted = 1
    db.session.commit()

def create_group(host, name, date):
	new_grp = Group(host=host,name=name,date=date)
    db.session.add(new_grp)
    db.session.flush()
    return new_grp.id

def invite_member(group, user):
	db.session.add(GroupMembers(user=user,group=group))
    db.session.commit()
    return user

def join_group(group, user):
	g = GroupMembers.query.filter_by(user=user,group=group,accepted=0)
    g.accepted = 1
    db.session.commit()
    return group

def submit_survey(group, loc_x, loc_y, price, dist, time, tags):
	g = Group.query.filter_by(group=group).first()
    g.tot_x += loc_x
    g.tot_y += loc_y
    g.tot_price += price
    g.tot_dist += dist
    g.tot_time += time
    for t in tags:
        try:
            tag = Tags(category = 'grp', name=group, tag=t)
            db.session.add(tag)
        except:
            pass 
    db.session.commit()
    return "Responses submitted"

def place_vote(group, restaurants):
	for i in range(len(restaurants) - 1):
        v = BordaVote(group=group,rank=i,restaurant=restaurants[i])
        db.session.add(v)
    db.session.commit()
    return "Vote submitted."

def leave_group(user, group):
    g = Group.query.filter_by(group=group).first()
	if g.host == user:
        db.session.delete(g)
    else:
        gm = GroupMembers.query.filter_by(user=user)
        db.session.delete(gm)
    db.session.commit()

def delete_user(user):
	usr = User.query.filter_by(id=user).first()
    db.session.delete(usr)
    f_ships = Friends.query.filter((Friends.inviter == usr) | 
    (Friends.invitee == usr)).all()
    for f in f_ships:
        db.session.delete(f)
    gm = GroupMembers.query.filter_by(user = user).all()
    for mem in gm:
        db.session.delete(mem)
    db.session.commit()
    return 

def make_restaurant(name, price, image, rating, description, wait, phone, 
                    loc_x, loc_y):
	res = Restaurants(name=name, price=price, image=image, rating=rating, 
                        description=description, wait_time=wait, 
                        phone=phone, loc_x=loc_x, loc_y=loc_y)
    db.session.add(res)
    db.session.commit()

def add_res_tags(res_id, tags):
    for t in tags:
        tg = Tags(category="res", name=res_id,tag=t)
        db.session.add(tg)
    db.session.commit()

def delete_restaurant(res_id):
    db.session.delete(Restaurants.query.filter_by(id=res_id).first())
    db.session.commit()

