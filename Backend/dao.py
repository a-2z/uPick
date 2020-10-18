from db import *
from exceptions import *

"""Database interaction methods"""

def get_user(user):
    """Return the username, friend list, and restriction list of the user
    as a dictionary."""
    pass

def get_user_name(user):
    """Check that the username exists in the username table"""
    un = User.query.filter_by(user=user).first()
    friends = []
    for friend in db.query(Friends).filter(_and(_or(Friends.inviter==user, Friends.invitee == user), Friends.accepted == 1)).all():
        f_ship = friend.serialize_friendship()
        if f_ship["f1"] == user:
            friends.append (f_ship["f2"])
        else:
            friends.append(f_ship["f1"])
    if un is None:
        raise UserNotFound(user)
    else:
        username = un.serialize_user()["user"]
        return {"id": username, "friends": friends}


def get_pending(user):
    """Get sent or recieved friend requests that are not accepted"""
    pass

def get_group(group):
    pass

def get_invites(user):
    pass

def create_user(user, hash):
    """Store a new user's username and hash"""
    try:
        new_user = User(user = user, hash_pw = hash)
        db.session.add(new_user)
        db.session.commit()
        return user
    except:
        raise UserExists(user)

"""return a user's hash"""
def authenticate(user, password):
    user_pass = User.query.filter_by(user=user).first()
    if user_pass is None:
        raise UserNotFound(user)
    else:
        return user_pass.serialize_user()["hash"]

def accept_friend(f1, f2):
	pass

def create_group(host, date):
	pass

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

