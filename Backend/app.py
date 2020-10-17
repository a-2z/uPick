import json
import json
from flask import Flask, request
from db import db
import dao

# Define db filename
app = Flask(__name__)
db_filename = "upick.db"

app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///%s" % db_filename
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SQLALCHEMY_ECHO"] = True

db.init_app(app)
with app.app_context():
    db.create_all

# Helper Methods


def success_response(data, code=200):
    """Returns a generic failure response with a specific error message
    as a json."""
    return json.dumps({"success": True, "data": data}), code


def failure_response(message, code=404):
    """Returns a generic failure response with a specific error message
    as a json."""
    return json.dumps({"success": False, "error": message}), code


def ju(jason):
    """Unpacks jsons"""
    return json.loads(jason)


# Routes go here
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
