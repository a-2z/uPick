import json
from flask import Flask, request
from db import db
import dao
import bcrypt
from exceptions import *

# Define db filename
app = Flask(__name__)
db_filename = "upick.db"

app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///%s" % db_filename
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = True

db.init_app(app)
with app.app_context():
    db.create_all()
    db.session.commit()

# Helper Methods


def success_response(data, code=200):
    """Returns a generic failure response with a specific error message
    as a json."""
    return json.dumps({"success": True, "data": data}), code


def failure_response(message, code=404):
    """Returns a generic failure response with a specific error message
    as a json."""
    return json.dumps({"success": False, "error": message}), code


def hash_pw(pwd):
    """Hash a password, integrating the salt into the hash"""
    return bcrypt.hashpw(pwd.encode('utf8'), bcrypt.gensalt())

def compare_pw(pwd, hash):
    """Compare the plaintext password with the stored hash"""
    return bcrypt.checkpw(pwd.encode('utf8'), hash)

def ju(jason):
    """Unpacks jsons"""
    return json.loads(jason)

# Routes go here
@app.route('/users/<int:user_id>', methods=['GET'])
def get_user(user_id):
    try:
        return success_response(dao.get_user(user_id))
    except UserNotFound as e:
        return failure_response("Please try again.")

@app.route('/pending/<int:user_id>', methods=['GET'])
def get_pending(user_id):
    try:
        return success_response(dao.get_pending(user_id))
    except:
        return failure_response("User not found.")

@app.route('/groups/<int:group_id>', methods=['GET'])
def get_group(group_id):
    return success_response(dao.get_group(group_id))
    try:
        return success_response(dao.get_group(group_id))
    except:
        return failure_response("Group not found")

@app.route('/invites/<int:user_id>', methods=['GET'])
def check_invites(user_id):
    try:
        return success_response(dao.get_invites(user_id))
    except:
        return failure_response("Group not found")

@app.route('/restaurants/<int:rest_id>', methods=['GET'])
def get_restaurant(rest_id):
    rest_info = dao.get_restaurant(rest_id)
    if rest_info is None:
        return failure_response(rest_info)
    else:
        return success_response(rest_info)

@app.route('/users/', methods=['POST'])
def new_user():
    """Create a new user account"""
    data = ju(request.data)
    usr = data["usr"]
    pwd = data["pwd"]
    try:
        return success_response(dao.create_user(usr,hash_pw(pwd)))
    except:
        return failure_response("Invalid username or password.")

@app.route('/signin/', methods=['POST'])
def login():
    """Sign in"""
    data = ju(request.data)
    usr = data["usr"]
    pwd = data["pwd"]
    try:
        if compare_pw(pwd, dao.authenticate(usr,pwd)):
            return success_response("You are logged in.")
        else:
            return failure_response("Invalid username password combination.")
    except:
        return failure_response("Invalid username or password.")

@app.route('/invites/', methods=['POST'])
def add_friend():
    data = ju(request.data)
    f1 = data["friend1"]
    f2 = data["friend2"]
    try:
        return success_response(dao.add_friend(f1, f2))
    except:
        return failure_response("User not found")

@app.route('/create/', methods=['POST'])
def create_group():
    data = ju(request.data)
    host = data["host"]
    group_name = data["name"]
    date = data["date"]
    try:
        return success_response(dao.create_group(host, group_name, date))
    except:
        return failure_response("Please try again.")

@app.route('/groups/', methods=['POST'])
def invite_member():
    data = ju(request.data)
    grp = data["group"]
    user = data["user"]
    try:
        return success_response(dao.invite_member(grp,user))
    except:
        return failure_response("Please try again.")

@app.route('/groups/join/', methods=['POST'])
def join_group():
    data = ju(request.data)
    grp = data["group"]
    user = data["user"]
    try:
        return success_response(dao.join_group(grp,user))
    except:
        return failure_response("Please try again.")

@app.route('/survey/', methods=['POST'])
def submit_survey():
    data = ju(request.data)
    group = data["group"]
    loc_x = data["location_x"]
    loc_y = data["location_y"]
    price = data["price"]
    dist = data["distance"]
    time = data["time"]
    tags = data["preferences"]
    try:
        return success_response(dao.submit_survey(group, loc_x, loc_y, price,
                                dist, time, tags))
    except:
        return failure_response("Please try again.")

@app.route('/vote/', methods=['POST'])
def place_vote():
    data = ju(request.data)
    group = data["group"]
    restaurants = data["restaurants"]
    try:
        return success_response(dao.place_vote(group,restaurants))
    except:
        return failure_response("Vote failed or group does not exist")

@app.route('/groups/', methods=['DELETE'])
def leave_group():
    """Leave a group if not host; delete it if host leaves"""
    data = ju(request.data)
    user = data["user"]
    group = data["group"]
    try:
        return success_response(dao.leave_group(user, group))
    except:
        return failure_response("User not in group.")

@app.route('/users/', methods=['DELETE'])
def delete_user():
    data = ju(request.data)
    user = data["user"]
    pwd = data["pwd"]
    try:
        return success_response(dao.delete_user(user))
    except:
        return failure_response("User not in group.")

#Private routes
@app.route('/restaurants/',methods=['POST'])
def add_restaurant():
    data = ju(request.data)
    name = data["name"]
    price = data["price"]
    image = data["image"]
    rating = data["rating"]
    description = data["description"]
    wait_time = data["wait_time"]
    phone = data["phone"]
    loc_x = data["loc_x"]
    loc_y = data["loc_y"]
    try: 
        return success_response(
            dao.make_restaurant(
                name, price, image, rating, description, wait_time, phone, 
                loc_x,loc_y)
                )
    except:
        return failure_response("Please try again.")

@app.route('/ingredients/',methods=['POST'])
def add_ingredients():
    data = ju(request.data)
    name = data["ingredients"]
    try: 
        return success_response(dao.add_ingredient(name))
    except:
        return failure_response("Please try again.")

@app.route('/restaurants/tags/', methods=['POST'])
def add_tags():
    data = ju(request.data)
    res_id = data["id"]
    tags = data["tags"]
    try: 
        return success_response(dao.add_res_tags(res_id,tags))
    except:
        return failure_response("Please try again.")

@app.route('/restaurants/', methods=['DELETE'])
def delete_restaurant():
    data = ju(request.data)
    res_id = data["id"]
    try: 
        return success_response(dao.delete_restaurant(res_id))
    except:
        return failure_response("Please try again.")

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
