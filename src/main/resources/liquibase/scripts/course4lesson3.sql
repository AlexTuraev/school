-- liquibase formatted sql

-- changeset alext:1
CREATE INDEX student_name_index ON student(name)

-- changeset alext:3
CREATE INDEX faculty_name_color_index ON faculty (name, color)