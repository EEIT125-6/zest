USE [DemoLab]
GO
/****** Object:  Table [dbo].[Board]    Script Date: 2020/12/13 下午 04:53:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Board](
	[boardid] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](20) NULL,
	[star] [int] NULL,
	[date] [datetime] NULL,
	[context] [nvarchar](max) NULL,
	[photo] [nvarchar](max) NULL,
	[id] [int] NULL,
	[Store_Id] [int] NULL,
 CONSTRAINT [PK_Board] PRIMARY KEY CLUSTERED 
(
	[boardid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductInfo]    Script Date: 2020/12/13 下午 04:53:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductInfo](
	[product_id] [int] NOT NULL,
	[product_name] [nvarchar](50) NOT NULL,
	[product_shop] [nvarchar](50) NOT NULL,
	[product_price] [int] NOT NULL,
	[product_picture] [nvarchar](max) NULL,
	[product_quantity] [int] NULL,
	[Store_Id] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Store]    Script Date: 2020/12/13 下午 04:53:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Store](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[stname] [nvarchar](50) NULL,
	[sclass] [nvarchar](50) NULL,
	[saddress] [nvarchar](50) NULL,
	[stitd] [nvarchar](200) NULL,
	[tel] [nvarchar](50) NULL,
	[bannerurl] [nvarchar](100) NULL,
	[photourl] [nvarchar](100) NULL,
	[stitddt] [nvarchar](255) NULL,
	[price] [int] NULL,
 CONSTRAINT [PK_Store] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Board]  WITH CHECK ADD  CONSTRAINT [FK80jr5j8bypf5mblrurl3b6qgq] FOREIGN KEY([Store_Id])
REFERENCES [dbo].[Store] ([id])
GO
ALTER TABLE [dbo].[Board] CHECK CONSTRAINT [FK80jr5j8bypf5mblrurl3b6qgq]
GO
ALTER TABLE [dbo].[ProductInfo]  WITH CHECK ADD  CONSTRAINT [FKdq6vipfa6ahnwjlwwwjrwo2nl] FOREIGN KEY([Store_Id])
REFERENCES [dbo].[Store] ([id])
GO
ALTER TABLE [dbo].[ProductInfo] CHECK CONSTRAINT [FKdq6vipfa6ahnwjlwwwjrwo2nl]
GO
