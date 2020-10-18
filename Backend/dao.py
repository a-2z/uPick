from db import *
from exceptions import *

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
    grp = Group.query.filter_by(id=group).first().serialize_group()
    mem = []
    for m in GroupMembers.filter_by(group=group,accepted=1).all():
        mem.append(m.serialize_group_mem()["user"])
    grp["members"] = mem
    num_ready = GroupMembers.filter_by(group=group,ready=1).count()
    if num_ready == len(mem):
        filter_restaurants(group)

def filter_restaurants(group):
    aggregate_prefs = Group.query.filter_by(id=group).first().serialize_internal()
    Restaurants.query.filter(
        (Restaurants.price <= aggregate_prefs["price"]) &
        (Restaurants.wait_time <= aggregate_prefs["time"]) &
        (Restaurants.wait_time <= aggregate_prefs["time"]) &
        
        )


    



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

"""return a user's hash"""
def authenticate(user, password):
    user_pass = User.query.filter_by(user=user).first()
    if user_pass is None:
        raise UserNotFound(user)
    else:
        return user_pass.serialize_user()["hash"]

def add_friend(f1, f2):
    """friendship = Friends(inviter=f1, invitee=f2)
    if db.session.query(Friends).filter(((Friends.inviter == user_id) 
    | (Friends.invitee == user_id)) & (Friends.accepted == 0)).count() > 0:"""

def create_group(host, name, date):
	new_grp = Group(host=host,name=name,date=date)
    db.session.add(new_grp)
    db.session.flush()
    return new_grp.id

def invite_member(group, user):
	pass

def join_group(group, user):
	pass

def submit_survey(loc_x, loc_y, price, dist, time, tags):
	pass

def place_vote(user, restaurants):
	pass

def leave_group(user, group):
	pass

def delete_group(group):
	pass

def delete_user(user):
	pass

def make_restaurant(name, price, rating, description, wait, phone, loc_x, loc_y):
	pass

def add_ingredient(name):
    pass

def add_res_tags(res_id, tags):
    pass

def delete_restaurant(res_id):
    pass

