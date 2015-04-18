
/* Drop Tables */

DROP TABLE IF EXISTS [media_path];
DROP TABLE IF EXISTS [media_item];
DROP TABLE IF EXISTS [movie_media_info];




/* Create Tables */

CREATE TABLE [media_item]
(
    [id] integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    [absolute_path] text NOT NULL,
    [hash_code] text NOT NULL,
    [parent_id] integer,
    [parent_type] text
);


CREATE TABLE [media_path]
(
    [id] integer NOT NULL PRIMARY KEY AUTOINCREMENT,
    [complete_path] text NOT NULL,
    [name] text NOT NULL,
    [is_visible] numeric DEFAULT 1 NOT NULL,
    [is_directory] numeric NOT NULL,
    [parent_id] integer NOT NULL,
    [media_item_id] integer NOT NULL,
    FOREIGN KEY ([parent_id])
    REFERENCES [media_path] ([id]),
    FOREIGN KEY ([media_item_id])
    REFERENCES [media_item] ([id])
);


CREATE TABLE [movie_media_info]
(
    [id] integer NOT NULL,
    [length] integer NOT NULL,
    [height] integer NOT NULL,
    [width] integer NOT NULL,
    [thumbnail_data] none,
    [thumbnail_width] integer,
    [thumbnail_height] integer,
    PRIMARY KEY ([id])
);



