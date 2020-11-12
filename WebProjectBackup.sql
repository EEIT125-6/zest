USE [master]
GO
/****** Object:  Database [WebProject]    Script Date: 2020/11/12 下午 08:27:23 ******/
CREATE DATABASE [WebProject]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'WebProject', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\WebProject.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'WebProject_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\WebProject_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [WebProject] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [WebProject].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [WebProject] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [WebProject] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [WebProject] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [WebProject] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [WebProject] SET ARITHABORT OFF 
GO
ALTER DATABASE [WebProject] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [WebProject] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [WebProject] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [WebProject] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [WebProject] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [WebProject] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [WebProject] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [WebProject] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [WebProject] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [WebProject] SET  DISABLE_BROKER 
GO
ALTER DATABASE [WebProject] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [WebProject] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [WebProject] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [WebProject] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [WebProject] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [WebProject] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [WebProject] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [WebProject] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [WebProject] SET  MULTI_USER 
GO
ALTER DATABASE [WebProject] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [WebProject] SET DB_CHAINING OFF 
GO
ALTER DATABASE [WebProject] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [WebProject] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [WebProject] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [WebProject] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [WebProject] SET QUERY_STORE = OFF
GO
USE [WebProject]
GO
/****** Object:  User [scott]    Script Date: 2020/11/12 下午 08:27:23 ******/
CREATE USER [scott] FOR LOGIN [scott] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [scott]
GO
ALTER ROLE [db_datareader] ADD MEMBER [scott]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [scott]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 2020/11/12 下午 08:27:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[bookingNo] [varchar](50) NOT NULL,
	[date] [date] NOT NULL,
	[time] [time](7) NOT NULL,
	[people] [int] NOT NULL,
	[restaurant] [nvarchar](50) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[phone] [int] NOT NULL,
	[email] [varchar](50) NOT NULL,
	[purpose] [nvarchar](50) NULL,
	[needs] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CityCode]    Script Date: 2020/11/12 下午 08:27:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CityCode](
	[city_code] [char](3) NOT NULL,
	[city_name] [nvarchar](3) NOT NULL,
 CONSTRAINT [PK_CityCode] PRIMARY KEY CLUSTERED 
(
	[city_code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductInfo]    Script Date: 2020/11/12 下午 08:27:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ProductInfo](
	[product_id] [int] NOT NULL,
	[product_name] [nvarchar](50) NOT NULL,
	[product_shop] [nvarchar](50) NOT NULL,
	[product_price] [int] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WebUser]    Script Date: 2020/11/12 下午 08:27:23 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WebUser](
	[user_id] [varchar](20) NOT NULL,
	[first_name] [nvarchar](3) NOT NULL,
	[last_name] [nvarchar](3) NOT NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[gender] [char](1) NULL,
	[birth] [date] NOT NULL,
	[fervor] [nvarchar](50) NULL,
	[get_email] [char](1) NOT NULL,
	[location_code] [char](3) NOT NULL,
	[join_date] [date] NOT NULL,
	[lv] [smallint] NOT NULL,
	[addr0] [nvarchar](65) NULL,
	[addr1] [nvarchar](65) NULL,
	[addr2] [nvarchar](65) NULL,
	[zest] [decimal](10, 2) NULL,
 CONSTRAINT [PK_WebUser] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't01', N'臺北市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't02', N'新北市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't03', N'桃園市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't04', N'臺中市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't05', N'臺南市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't06', N'高雄市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't07', N'基隆市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't08', N'新竹市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't09', N'嘉義市')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't10', N'新竹縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't11', N'苗栗縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't12', N'彰化縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't13', N'南投縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't14', N'雲林縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't15', N'嘉義縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't16', N'屏東縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't17', N'宜蘭縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't18', N'花蓮縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't19', N'臺東縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't20', N'澎湖縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't21', N'金門縣')
INSERT [dbo].[CityCode] ([city_code], [city_name]) VALUES (N't22', N'連江縣')
GO
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (100, N'串燒鰻魚飯', N'小東門蒸烤鮮飯食', 350)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (101, N'麻辣鮮蚵公仔麵', N'小東門蒸烤鮮飯食', 300)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (102, N'避風塘鮮蚵蓋飯', N'小東門蒸烤鮮飯食', 450)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (103, N'波波蚵', N'小東門蒸烤鮮飯食', 250)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (200, N'酥炸和風雞塊', N'靜岡勝政豬排', 150)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (201, N'明太子玉子燒', N'靜岡勝政豬排', 90)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (202, N'連貞豚厚切里肌豬排', N'靜岡勝政豬排', 200)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (203, N'炸蝦套餐', N'靜岡勝政豬排', 250)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (204, N'招牌炸豬排', N'靜岡勝政豬排', 180)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (300, N'招牌豚骨叉燒拉麵', N'拉麵吧', 370)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (301, N'豚骨雞腿肉串燒拉麵', N'拉麵吧', 320)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (302, N'豚骨日式豬排拉麵', N'拉麵吧', 300)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (303, N'地獄鮮蝦拉麵', N'拉麵吧', 280)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (304, N'地獄叉燒拉麵', N'拉麵吧', 240)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (400, N'霜降和牛飯', N'硬派主廚的軟嫩料理', 300)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (401, N'鮮牛肉湯', N'硬派主廚的軟嫩料理', 320)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (402, N'鮮嫩牛肉飯', N'硬派主廚的軟嫩料理', 240)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (403, N'炙燒煙燻牛排', N'硬派主廚的軟嫩料理', 280)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (500, N'特製香蒜麵包', N'RAINBOWPAPA', 50)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (501, N'茄汁意麵', N'RAINBOWPAPA', 150)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (502, N'雙色奶酪', N'RAINBOWPAPA', 80)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (503, N'冰萃美式咖啡', N'RAINBOWPAPA', 100)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (504, N'手作鬆餅', N'RAINBOWPAPA', 70)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (505, N'炸物拼盤', N'RAINBOWPAPA', 100)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (507, N'芒果蛋糕', N'RAINBOWPAPA', 110)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price]) VALUES (506, N'酥皮濃湯', N'RAINBOWPAPA', 90)
GO
ALTER TABLE [dbo].[WebUser] ADD  CONSTRAINT [DF_WebUser_get_email]  DEFAULT ('Y') FOR [get_email]
GO
ALTER TABLE [dbo].[WebUser] ADD  CONSTRAINT [DF_Table_1_level]  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[WebUser] ADD  CONSTRAINT [DF_WebUser_zest]  DEFAULT ((0)) FOR [zest]
GO
USE [master]
GO
ALTER DATABASE [WebProject] SET  READ_WRITE 
GO
