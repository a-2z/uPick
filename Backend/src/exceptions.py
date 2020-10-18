class UserNotFound(Exception):
    """Raised if no rows returned for query of the users table"""
    pass

class UserExists(Exception):
    """Raised if a given user already exists in the database"""
    pass
