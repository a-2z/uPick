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
    if un is None:
        raise UserNotFound(user)
    else:
        un.serialize_user()

def get_pending(user):
	pass

def get_group(group):
	pass

def get_invites(user):
	pass

def create_user(user, password):
	pass

def authenticate(user, password):
	pass

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

