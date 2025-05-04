-- SQL script to modify the users table by making the username field nullable
-- or removing it entirely if it's not needed

-- Option 1: Make username column nullable
ALTER TABLE users MODIFY username VARCHAR(255) NULL;

-- Option 2: Remove username column entirely
-- Uncomment this if you're sure you want to completely remove the column
-- ALTER TABLE users DROP COLUMN username; 