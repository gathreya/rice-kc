CREATE TABLE KCB_MESSAGES
(
    ID                  NUMBER(8) NOT NULL,
    DELIVERY_TYPE       VARCHAR2(500) NOT NULL,
    CREATED_DATETIME    TIMESTAMP NOT NULL,
    TITLE               VARCHAR2(255) NULL,
    CHANNEL             VARCHAR2(300) NULL,
    PRODUCER            VARCHAR2(300) NULL,
    CONTENT             CLOB NOT NULL,
    CONTENT_TYPE        VARCHAR2(128) NOT NULL,
    USER_RECIPIENT_ID   VARCHAR2(300) NOT NULL,
    DB_LOCK_VER_NBR     INTEGER DEFAULT 0 NOT NULL,

    CONSTRAINT KCB_MESSAGES_PK PRIMARY KEY (ID)
)
/