USE [WebProject]
GO
SET IDENTITY_INSERT [dbo].[CityInfo] ON 
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (1, N'�O�_��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (2, N'�s�_��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (3, N'��饫')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (4, N'�O����')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (5, N'�O�n��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (6, N'������')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (7, N'�򶩥�')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (8, N'�s�˥�')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (9, N'�Ÿq��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (10, N'�s�˿�')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (11, N'�]�߿�')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (12, N'���ƿ�')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (13, N'�n�뿤')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (14, N'���L��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (15, N'�Ÿq��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (16, N'�̪F��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (17, N'�y����')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (18, N'�Ὤ��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (19, N'�O�F��')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (20, N'���')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (21, N'������')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (22, N'�s����')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (23, N'��L')
SET IDENTITY_INSERT [dbo].[CityInfo] OFF
GO
SET IDENTITY_INSERT [dbo].[FoodFervor] ON 
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (1, N'����')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (2, N'���\')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (3, N'�N��')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (4, N'�覡')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (5, N'�U�ȯ�')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (6, N'�馡')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (7, N'�ҥi')
SET IDENTITY_INSERT [dbo].[FoodFervor] OFF
GO
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES ('M', N'�k��')
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES ('W', N'�k��')
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES ('N', N'����K����')
GO
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (-1, N'�޲z��')
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (0, N'���O��')
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (1, N'���a')
GO
INSERT [dbo].[Willing] ([willingCode], [willingText]) VALUES ('Y', N'�@�N')
INSERT [dbo].[Willing] ([willingCode], [willingText]) VALUES ('N', N'���@�N')
GO 
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES ('0000000', N'WebAdmin', N'��饫���c�Ϥ��j��300��', N'', N'', CAST(N'1990-01-01' AS Date), N'notARealEmail@gmail.com', N'�ҥi', N'��', CAST(N'2020-11-04' AS Date), N'����', N'�޲z��', N'WebAdmin2020', N'0955779933', N'active', 0, CAST(0.00 AS Decimal(19, 2)), -1, N'N', N'N', 3, N'')
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstname], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES ('1000000', N'TestUser', N'��饫���c�Ϥ��j��300��', N'', N'', CAST(N'2000-10-10' AS Date), N'notARealEmail@yahoo.com', N'����,���\,�N��,�覡,�U�ȯ�,�馡,�ҥi', N'�d', CAST(N'2020-11-04' AS Date), N'����', N'�L���H', N'TestUser1256', N'0911223344', N'active', 0, CAST(0.00 AS Decimal(19, 2)), 0, N'M', N'N', 3, N'')
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES ('2000000', N'TestBoss', N'��饫���c�Ϥ��j��300��', N'', N'', CAST(N'1980-12-31' AS Date), N'notARealEmail@outlook.com', N'����,���\,�N��', N'�q', CAST(N'2020-11-04' AS Date), N'�Y�a', N'�n�Y�a', N'TestBossZest1104', N'0933441122', N'active', 0, CAST(0.00 AS Decimal(19, 2)), 1, N'W', N'N', 3, N'')
GO


