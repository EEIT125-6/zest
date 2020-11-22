USE [DemoLab]
GO
/****** Object:  Table [dbo].[Store]    Script Date: 2020/11/22 下午 01:47:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Store](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[stname] [nvarchar](50) NULL,
	[sclass] [nvarchar](50) NULL,
	[saddress] [nvarchar](50) NULL,
	[stitd] [nvarchar](50) NULL,
	[tel] [nvarchar](50) NULL,
	[bannerurl] [nvarchar](100) NULL,
	[photourl] [nvarchar](100) NULL
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Store] ON 
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (1, N'燒肉同話', N'燒肉', N'桃園市', N'燒肉很好吃', N'03-4532459', N'Images/Chagether-banner.jpg', N'Images/11.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (10, N'牧水巷老爺製作所', N'下午茶', N'台南市中西區開山路35巷1弄6號', N'網美IG打卡熱點
營業時間：13:00-20:00
公休日：每週一', N'0906142995', N'Images/20200207180238_30.jpg', N'Images/SA.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (2, N'鬥佐燒肉', N'燒肉', N'新竹市', N'燒肉很好吃喔', N'03-4532459', N'Images/鬥佐燒肉banner.jsp.jpg', N'Images/鬥佐燒肉.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (3, N'森日式料理', N'日式', N'台中市', N'就是個日式料理', N'03-4532459', N'Images/jpbanner.jpg', N'Images/IMG_4174.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (4, N'瑞記海南雞飯', N'中式', N'台北市', N'雞肉飯不好吃', N'03-4532459', N'Images/banner_page-0001.jpg', N'Images/1538116798-1112015215_l.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (5, N'地平線1號', N'快餐', N'屏東市', N'地平線一號', N'03-4532459', N'Images/地平線1號BANNER.jpg', N'Images/地平線1號.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (6, N'下午茶好吃', N'下午茶', N'資策會隔壁', N'偷懶用的', N'03-45324599', N'Images/20190922214643_51.jpg', N'Images/3_79.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (7, N'熱浪島', N'西式', N'中壢市', N'我覺得很好吃
', N'03-03030303', N'Images/169a06521c9336e859d5c925c514b265熱浪BANNER.jpg', N'Images/79a9bff0-8aab-11ea-bb37-665344352c09熱浪島.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (8, N'瓦城', N'西式', N'新光三越', N'泰式料理', N'....', N'Images/1561021689-87fa104e205e3d642206724b31b4be95BAN.jpg', N'Images/瓦城載.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (9, N'大腕燒肉', N'燒肉', N'106台北市大安區敦化南路一段177巷22號1樓', N'米其林燒肉
', N' 02 2711 0179', N'Images/大腕燒肉banner.jpg', N'Images/大腕燒肉.jpg')
GO
SET IDENTITY_INSERT [dbo].[Store] OFF
GO
