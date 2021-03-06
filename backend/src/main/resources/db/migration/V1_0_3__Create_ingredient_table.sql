CREATE TABLE CB_INGREDIENT (
  ID BIGINT NOT NULL AUTO_INCREMENT,
  AMOUNT DOUBLE NOT NULL,
  UNIT VARCHAR(30) NOT NULL,
  NAME VARCHAR(100) NOT NULL,
  FK_RECIPE_ID BIGINT,

  CONSTRAINT FK_RECIPE FOREIGN KEY(FK_RECIPE_ID) REFERENCES CB_RECIPE(ID),
  CONSTRAINT PK_INGREDIENT PRIMARY KEY(ID)
)