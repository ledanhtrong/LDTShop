﻿

IF NOT EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'ASM')
	CREATE DATABASE ASM


--------------------ACCOUNT-------------------

USE ASM

IF OBJECT_ID('ACCOUNT') IS NOT NULL
	DROP TABLE ACCOUNT

CREATE TABLE ACCOUNT(
	USERNAME	VARCHAR(30)		PRIMARY KEY,
	PASSWORD	VARCHAR(500)	NOT NULL,
	FULLNAME	NVARCHAR(50)	NULL,
	PHONE		VARCHAR(20)		NULL,
	ADDRESS		NVARCHAR(500)	NULL,
	ROLE		INT				NOT NULL
)

INSERT	INTO ACCOUNT(USERNAME, FULLNAME, ROLE, PASSWORD, PHONE, ADDRESS)
VALUES	('wifildt', N'Trọng', 2, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '090485734', N'Quận 1'   ),
		('account1', N'Khánh', 1, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '090485734', N'Hà Nội'),
		('account2', N'Vân', 1, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92','090485734', N'Hồ Chí Minh'),
		('account3', N'Thành', 0, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '090485734', N'Số 1 Lê Duẫn'),
		('account4', N'Hòa', 0, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92', '090485734', N'Đại học FPT quận 9 lô xyz');

SELECT * FROM ACCOUNT

----------------Product-----------------------

USE ASM

IF OBJECT_ID('PRODUCT') IS NOT NULL
	DROP TABLE PRODUCT

CREATE TABLE PRODUCT (
	ID			VARCHAR(100)	PRIMARY KEY,
	TYPEID		VARCHAR(20)		NOT NULL,
	NAME		NVARCHAR(100)	NOT NULL,
	PRICE		FLOAT			NOT NULL,
	UNIT		NVARCHAR(100)	NULL,
	QUANTITY	INTEGER			NOT NULL,
	IMAGE		VARCHAR(250)	NULL,
	DESCRIPTION NVARCHAR(100)	NULL
)

INSERT INTO PRODUCT(ID, TYPEID, NAME, PRICE, UNIT, QUANTITY, DESCRIPTION, IMAGE)
VALUES	('001', 'DK',N'Coca', 10000, N'Lon', 100, N'Nước ngọt có ga', ''),
		('002', 'DK',N'Pepsi', 10000, N'Lon', 700, N'Nước ngọt có ga', ''),
		('003', 'DK',N'String', 10000, N'Lon', 200, N'Nước ngọt có ga', ''),
		('004', 'CL',N'Áo thun', 100000, N'Cái', 333, N'Làm từ 100% cotton', ''),
		('005', 'CL',N'Áo ba lỗ', 60000, N'Cái', 450, N'Siêu thấm', ''),
		('006', 'CL',N'Quần GAP', 400000, N'Cái', 77, N'Quần jeans nam', ''),
		('007', 'FR',N'Táo', 50000, N'Ký', 50, N'Táo xanh từ Mỹ', ''),
		('008', 'FR',N'Cherry', 500000, N'Hộp', 120, N'Nhập từ Úc, mỗi hộp nữa ký', ''),
		('009', 'FR',N'Ổi', 50000, N'Ký', 100, N'Ổi trắng thơm ngon', ''),
		('010', 'FR',N'Xoài cát Hòa Lộc', 200000, N'Ký', 220, N'Sản phẩm sạch từ vùng ĐB Sông Cửu Long', '');

SELECT * FROM PRODUCT

-----------TYPE--------------
USE ASM

IF OBJECT_ID('PRODUCT_TYPE') IS NOT NULL
	DROP TABLE PRODUCT_TYPE

CREATE TABLE PRODUCT_TYPE (
	ID		VARCHAR(20)		PRIMARY KEY,
	NAME	NVARCHAR(100)	NOT NULL
)

INSERT INTO PRODUCT_TYPE (ID, NAME)
VALUES ('DK', N'Đồ uống'),
		('CL', N'Quần áo'),
		('FR', N'Trái cây');

SELECT * FROM PRODUCT_TYPE

---------------------CART---------------------

USE ASM

IF OBJECT_ID('CART') IS NOT NULL
	DROP TABLE CART

CREATE TABLE CART(
	USERNAME	VARCHAR(30)		NOT NULL,
	PRODUCTID	VARCHAR(100)	NOT NULL,
	QUANTITY	INTEGER			NOT NULL,
	CONSTRAINT CART_PK PRIMARY KEY (USERNAME, PRODUCTID) 
)

SELECT * FROM CART

--------------------ORDER------------------------

USE ASM

IF OBJECT_ID('ORDERS') IS NOT NULL
	DROP TABLE ORDERS

CREATE TABLE ORDERS(
	ORDERID		INT	IDENTITY(1,1) NOT NULL UNIQUE,
	USERNAME	VARCHAR(30)		NOT NULL,
	PAIDDAY		DATETIME		NOT NULL,
	BUYER		NVARCHAR(50)	NULL,
	RECEIVER	NVARCHAR(50)	NULL,
	ADDRESS		NVARCHAR(500)	NOT NULL,
	PHONE		VARCHAR(20)		NULL,
	SUBTOTAL	FLOAT			NULL,
	CONSTRAINT ORDERS_PK PRIMARY KEY (USERNAME, PAIDDAY)
)

SELECT * FROM ORDERS

--------------ORDER_DETAIL

USE ASM

IF OBJECT_ID('ORDER_DETAILS') IS NOT NULL
	DROP TABLE ORDER_DETAILS

CREATE TABLE ORDER_DETAILS(
	ORDERID		INT				NOT NULL,
	PRODUCTID	VARCHAR(100)	NOT NULL,
	NAME		NVARCHAR(100)	NOT NULL,
	QUANTITY	INTEGER			NOT NULL,
	PRICE		FLOAT			NOT NULL,
	TOTAL		FLOAT			NULL,
	CONSTRAINT	DETAIL_PK	PRIMARY KEY (ORDERID, PRODUCTID)
)

SELECT * FROM ORDER_DETAILS
