USE [master]
GO
/****** Object:  Database [WebProject]    Script Date: 2020/12/18 下午 09:12:20 ******/
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
/****** Object:  User [scott]    Script Date: 2020/12/18 下午 09:12:20 ******/
CREATE USER [scott] FOR LOGIN [scott] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [scott]
GO
ALTER ROLE [db_datareader] ADD MEMBER [scott]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [scott]
GO
/****** Object:  Table [dbo].[AreaCode]    Script Date: 2020/12/18 下午 09:12:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AreaCode](
	[areaCode] [int] NOT NULL,
	[areaName] [nvarchar](4) NOT NULL,
	[cityCode] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[areaCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Board]    Script Date: 2020/12/18 下午 09:12:21 ******/
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
	[rstid] [int] NULL,
 CONSTRAINT [PK_Board] PRIMARY KEY CLUSTERED 
(
	[boardid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[booking]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[booking](
	[bookingNo] [char](5) NOT NULL,
	[bookingdate] [varchar](255) NOT NULL,
	[mail] [varchar](255) NOT NULL,
	[name] [nvarchar](20) NOT NULL,
	[needs] [nvarchar](40) NOT NULL,
	[number] [int] NOT NULL,
	[phone] [varchar](255) NOT NULL,
	[purpose] [varchar](255) NOT NULL,
	[restaurant] [nvarchar](100) NOT NULL,
	[status] [int] NOT NULL,
	[time] [varchar](255) NOT NULL,
	[user_id] [varchar](255) NULL,
 CONSTRAINT [PK__booking__C6D06266D6825A2E] PRIMARY KEY CLUSTERED 
(
	[bookingNo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CityCode]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CityCode](
	[cityCode] [int] IDENTITY(1,1) NOT NULL,
	[cityName] [nvarchar](3) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cityCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CityInfo]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CityInfo](
	[cityCode] [int] IDENTITY(1,1) NOT NULL,
	[cityName] [nvarchar](3) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[cityCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[customerId] [int] IDENTITY(1,1) NOT NULL,
	[birthday] [date] NULL,
	[email] [varchar](255) NULL,
	[lastPostTime] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[photo] [varbinary](max) NULL,
	[registerTime] [datetime2](7) NULL,
	[totalPayment] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[customerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FoodFervor]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FoodFervor](
	[fervorCode] [int] IDENTITY(1,1) NOT NULL,
	[fervorItem] [nvarchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[fervorCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ProductInfo]    Script Date: 2020/12/18 下午 09:12:21 ******/
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
/****** Object:  Table [dbo].[Store]    Script Date: 2020/12/18 下午 09:12:21 ******/
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
/****** Object:  Table [dbo].[UserGender]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserGender](
	[genderCode] [char](1) NOT NULL,
	[genderText] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[genderCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserLevel]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserLevel](
	[lv] [int] NOT NULL,
	[levelName] [nvarchar](5) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[lv] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WebUser]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WebUser](
	[user_id] [char](7) NOT NULL,
	[account] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[first_name] [nvarchar](3) NOT NULL,
	[last_name] [nvarchar](3) NOT NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[gender] [char](1) NOT NULL,
	[birth] [date] NOT NULL,
	[fervor] [nvarchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[get_email] [char](1) NOT NULL,
	[location_code] [char](3) NOT NULL,
	[join_date] [date] NOT NULL,
	[lv] [smallint] NOT NULL,
	[addr0] [nvarchar](65) NOT NULL,
	[addr1] [nvarchar](65) NULL,
	[addr2] [nvarchar](65) NULL,
	[zest] [decimal](10, 2) NOT NULL,
	[version] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WebUserData]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WebUserData](
	[userId] [char](7) NOT NULL,
	[account] [varchar](20) NOT NULL,
	[addr0] [nvarchar](65) NOT NULL,
	[addr1] [nvarchar](65) NULL,
	[addr2] [nvarchar](65) NULL,
	[birth] [date] NOT NULL,
	[email] [varchar](50) NOT NULL,
	[fervor] [nvarchar](50) NOT NULL,
	[firstName] [nvarchar](3) NOT NULL,
	[gender] [char](1) NOT NULL,
	[getEmail] [char](1) NOT NULL,
	[joinDate] [date] NOT NULL,
	[lastName] [nvarchar](3) NOT NULL,
	[locationCode] [char](3) NOT NULL,
	[lv] [int] NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[status] [varchar](8) NULL,
	[version] [int] NULL,
	[zest] [decimal](19, 2) NULL,
PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WebUserDeleted]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WebUserDeleted](
	[user_id] [char](7) NOT NULL,
	[account] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[first_name] [nvarchar](3) NOT NULL,
	[last_name] [nvarchar](3) NOT NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[gender] [char](1) NOT NULL,
	[birth] [date] NOT NULL,
	[fervor] [nvarchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[get_email] [char](1) NOT NULL,
	[location_code] [char](3) NOT NULL,
	[join_date] [date] NOT NULL,
	[lv] [smallint] NOT NULL,
	[addr0] [nvarchar](65) NOT NULL,
	[addr1] [nvarchar](65) NULL,
	[addr2] [nvarchar](65) NULL,
	[zest] [decimal](10, 2) NOT NULL,
	[version] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[WebUserInfo]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[WebUserInfo](
	[userId] [char](7) NOT NULL,
	[account] [varchar](20) NOT NULL,
	[addr0] [nvarchar](65) NOT NULL,
	[addr1] [nvarchar](65) NULL,
	[addr2] [nvarchar](65) NULL,
	[birth] [date] NOT NULL,
	[email] [varchar](50) NOT NULL,
	[fervor] [nvarchar](50) NOT NULL,
	[firstName] [nvarchar](3) NOT NULL,
	[joinDate] [date] NOT NULL,
	[lastName] [nvarchar](3) NOT NULL,
	[nickname] [nvarchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[status] [varchar](8) NULL,
	[version] [int] NULL,
	[zest] [decimal](19, 2) NULL,
	[lv] [int] NULL,
	[genderCode] [char](1) NULL,
	[willingCode] [char](1) NULL,
	[cityCode] [int] NULL,
	[iconUrl] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[userId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Willing]    Script Date: 2020/12/18 下午 09:12:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Willing](
	[willingCode] [char](1) NOT NULL,
	[willingText] [nvarchar](3) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[willingCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Board] ON 

INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (1, N'George', 4, CAST(N'2020-11-20T00:00:00.000' AS DateTime), N'pic', N'aPic', 1, 1, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (2, N'GeorgeTseng', 4, CAST(N'2020-11-22T00:00:00.000' AS DateTime), N'Test20201122', N'20201122', 1, 1, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (3, N'teeeet', 5, CAST(N'2020-12-11T17:26:05.067' AS DateTime), N'hyju6j6uj6u4444', N'regth', 2, NULL, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (4, N'曾丙顥', 5, CAST(N'2020-11-24T00:00:00.000' AS DateTime), N'dfijdifoidhfhdhfd', NULL, 2, 2, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (5, N'黃老千', 3, CAST(N'2020-11-24T14:54:24.307' AS DateTime), N'怒吃', NULL, 3, 3, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (6, N'巴拉拉', 4, CAST(N'2020-11-24T14:56:03.273' AS DateTime), N'好吃', NULL, 3, 3, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (7, N'AYAYIYAIYA', 3, CAST(N'2020-11-26T21:28:25.833' AS DateTime), N'Cars stop thinking', N'No Pic', 4, 4, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (8, N'QQ奶茶', 3, CAST(N'2020-12-11T16:17:27.457' AS DateTime), N'測試', NULL, NULL, NULL, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (9, N'QQ奶茶', 3, CAST(N'2020-12-11T17:15:39.047' AS DateTime), N'測試123456', N'null', NULL, NULL, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (10, N'黃老千', 3, CAST(N'2020-12-11T17:16:20.947' AS DateTime), N'怒吃吃', N'null', NULL, NULL, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (12, N'ioooo', 4, CAST(N'2020-12-18T19:28:06.933' AS DateTime), N'sfafdsfsafsfsa', NULL, NULL, 1, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (13, N'ios', 5, CAST(N'2020-12-18T19:33:52.937' AS DateTime), N'fehyhyj', NULL, NULL, 1, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (14, N'張阿勳', 2, CAST(N'2020-12-18T19:38:29.537' AS DateTime), N'gkgjhvhvnv', NULL, NULL, 3, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (15, N'yoooooooo', 3, CAST(N'2020-12-18T19:41:11.673' AS DateTime), N'dwfgtgt', NULL, NULL, 4, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (16, N'uuuuuuuuu', 4, CAST(N'2020-12-18T19:55:08.180' AS DateTime), N'sdgfgthyhyj', NULL, NULL, 6, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (17, N'kkkkkkkkkk', 1, CAST(N'2020-12-18T20:06:23.930' AS DateTime), N'kjhukgkfjfjhf', NULL, NULL, 12, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (18, N'hhhhhh', 4, CAST(N'2020-12-18T20:57:22.297' AS DateTime), N'fdhghgjyju', NULL, NULL, 12, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (19, N'xxxxxxx', 5, CAST(N'2020-12-18T20:59:35.193' AS DateTime), N',llsfg;zsfmxzmkmvbxclmb', NULL, NULL, 7, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (20, N'zzzzzzz', 4, CAST(N'2020-12-18T21:01:51.457' AS DateTime), N'dfasfafsasafsa', NULL, NULL, 17, NULL)
INSERT [dbo].[Board] ([boardid], [name], [star], [date], [context], [photo], [id], [Store_Id], [rstid]) VALUES (21, N'喔', 5, CAST(N'2020-12-18T21:09:33.407' AS DateTime), N'hjkjhkjhjhjhjhjhjhjhkjhjhhj', NULL, NULL, 17, NULL)
SET IDENTITY_INSERT [dbo].[Board] OFF
GO
INSERT [dbo].[booking] ([bookingNo], [bookingdate], [mail], [name], [needs], [number], [phone], [purpose], [restaurant], [status], [time], [user_id]) VALUES (N'AVP2P', N'2020-12-16', N'regerh@gmail.com', N'Gary', N'', 1, N'0920310100', N'normal', N'靜岡勝政豬排', 1, N'12', N'')
INSERT [dbo].[booking] ([bookingNo], [bookingdate], [mail], [name], [needs], [number], [phone], [purpose], [restaurant], [status], [time], [user_id]) VALUES (N'EKZ0D', N'2020-12-31', N'htrhrh@gmail.com', N'劉哲文', N'綱手', 1, N'0933590254', N'normal', N'', 1, N'12', N'')
INSERT [dbo].[booking] ([bookingNo], [bookingdate], [mail], [name], [needs], [number], [phone], [purpose], [restaurant], [status], [time], [user_id]) VALUES (N'FV4VE', N'2020-12-12', N'rgeg@gmail.com', N'Gary', N'', 1, N'0920310100', N'normal', N'null', 0, N'12', N'')
INSERT [dbo].[booking] ([bookingNo], [bookingdate], [mail], [name], [needs], [number], [phone], [purpose], [restaurant], [status], [time], [user_id]) VALUES (N'STN2X', N'2020-12-31', N'wu@yahoo.com.tw', N'吳三桂', N'單身跨年party', 2, N'0988664466', N'normal', N'Woosaパンケーキ 屋莎鬆餅屋 桃園三越站前店', 0, N'19', N'')
INSERT [dbo].[booking] ([bookingNo], [bookingdate], [mail], [name], [needs], [number], [phone], [purpose], [restaurant], [status], [time], [user_id]) VALUES (N'XEYSH', N'2020-12-28', N'wu@yahoo.com.tw', N'吳三桂', N'', 1, N'0988664466', N'normal', N'瑞記海南雞飯', 1, N'12', N'')
GO
SET IDENTITY_INSERT [dbo].[CityCode] ON 

INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (23, N'其他')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (17, N'宜蘭縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (18, N'花蓮縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (21, N'金門縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (13, N'南投縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (16, N'屏東縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (11, N'苗栗縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (3, N'桃園市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (6, N'高雄市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (7, N'基隆市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (22, N'連江縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (14, N'雲林縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (2, N'新北市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (8, N'新竹市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (10, N'新竹縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (9, N'嘉義市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (15, N'嘉義縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (12, N'彰化縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (4, N'臺中市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (1, N'臺北市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (19, N'臺東縣')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (5, N'臺南市')
INSERT [dbo].[CityCode] ([cityCode], [cityName]) VALUES (20, N'澎湖縣')
SET IDENTITY_INSERT [dbo].[CityCode] OFF
GO
SET IDENTITY_INSERT [dbo].[CityInfo] ON 

INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (23, N'其他')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (17, N'宜蘭縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (18, N'花蓮縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (21, N'金門縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (13, N'南投縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (16, N'屏東縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (11, N'苗栗縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (3, N'桃園市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (6, N'高雄市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (7, N'基隆市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (22, N'連江縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (14, N'雲林縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (2, N'新北市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (8, N'新竹市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (10, N'新竹縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (9, N'嘉義市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (15, N'嘉義縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (12, N'彰化縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (4, N'臺中市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (1, N'臺北市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (19, N'臺東縣')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (5, N'臺南市')
INSERT [dbo].[CityInfo] ([cityCode], [cityName]) VALUES (20, N'澎湖縣')
SET IDENTITY_INSERT [dbo].[CityInfo] OFF
GO
SET IDENTITY_INSERT [dbo].[Customer] ON 

INSERT [dbo].[Customer] ([customerId], [birthday], [email], [lastPostTime], [name], [password], [photo], [registerTime], [totalPayment]) VALUES (1, CAST(N'1995-11-22' AS Date), N'brunomraz123@gmail.com', NULL, N'陳則安', N'admin', NULL, CAST(N'2020-12-12T14:43:03.9420000' AS DateTime2), 55.04)
SET IDENTITY_INSERT [dbo].[Customer] OFF
GO
SET IDENTITY_INSERT [dbo].[FoodFervor] ON 

INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (5, N'下午茶')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (1, N'中式')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (6, N'日式')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (4, N'西式')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (2, N'快餐')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (7, N'皆可')
INSERT [dbo].[FoodFervor] ([fervorCode], [fervorItem]) VALUES (3, N'燒肉')
SET IDENTITY_INSERT [dbo].[FoodFervor] OFF
GO
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (100, N'串燒鰻魚飯', N'小東門蒸烤鮮飯食', 350, N'../productInfo/images/串燒鰻魚飯.jpg', 0, 16)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (101, N'麻辣鮮蚵公仔麵', N'小東門蒸烤鮮飯食', 300, N'../productInfo/images/麻辣鮮蚵公仔麵.jpg', 0, 16)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (102, N'避風塘鮮蚵蓋飯', N'小東門蒸烤鮮飯食', 450, N'../productInfo/images/避風塘鮮蚵蓋飯.jpg', 0, 16)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (103, N'波波蚵', N'小東門蒸烤鮮飯食', 250, N'../productInfo/images/波波蚵.jpg', 0, 16)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (200, N'酥炸和風雞塊', N'靜岡勝政豬排', 150, N'../productInfo/images/酥炸和風雞塊.jpg', 0, 17)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (201, N'明太子玉子燒', N'靜岡勝政豬排', 90, N'../productInfo/images/明太子玉子燒.jpg', 0, 17)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (202, N'連貞豚厚切里肌豬排', N'靜岡勝政豬排', 200, N'../productInfo/images/連貞豚厚切里肌豬排.jpg', 0, 17)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (203, N'炸蝦套餐', N'靜岡勝政豬排', 250, N'../productInfo/images/炸蝦套餐.jpg', 0, 17)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (204, N'招牌炸豬排', N'靜岡勝政豬排', 180, N'../productInfo/images/招牌炸豬排.jpg', 0, 17)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (300, N'招牌豚骨叉燒拉麵', N'拉麵吧', 370, N'../productInfo/images/招牌豚骨叉燒拉麵.jpg', 0, 18)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (301, N'豚骨雞腿肉串燒拉麵', N'拉麵吧', 320, N'../productInfo/images/豚骨雞腿肉串燒拉麵.jpg', 0, 18)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (302, N'豚骨日式豬排拉麵', N'拉麵吧', 300, N'../productInfo/images/豚骨日式豬排拉麵.jpg', 0, 18)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (303, N'地獄鮮蝦拉麵', N'拉麵吧', 280, N'../productInfo/images/地獄鮮蝦拉麵.jpg', 0, 18)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (304, N'地獄叉燒拉麵', N'拉麵吧', 240, N'../productInfo/images/地獄叉燒拉麵.jpg', 0, 18)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (400, N'霜降和牛飯', N'硬派主廚的軟嫩料理', 300, N'../productInfo/images/霜降和牛飯.jpg', 0, 19)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (401, N'鮮牛肉湯', N'硬派主廚的軟嫩料理', 320, N'../productInfo/images/鮮牛肉湯.jpg', 0, 19)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (402, N'鮮嫩牛肉飯', N'硬派主廚的軟嫩料理', 240, N'../productInfo/images/鮮嫩牛肉飯.jpg', 0, 19)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (403, N'炙燒煙燻牛排', N'硬派主廚的軟嫩料理', 280, N'../productInfo/images/炙燒煙燻牛排.jpg', 0, 19)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (500, N'特製香蒜麵包', N'RAINBOWPAPA', 50, N'../productInfo/images/特製香蒜麵包.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (501, N'茄汁意麵', N'RAINBOWPAPA', 150, N'../productInfo/images/茄汁意麵.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (502, N'雙色奶酪', N'RAINBOWPAPA', 80, N'../productInfo/images/雙色奶酪.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (503, N'冰萃美式咖啡', N'RAINBOWPAPA', 100, N'../productInfo/images/冰萃美式咖啡.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (504, N'手作鬆餅', N'RAINBOWPAPA', 70, N'../productInfo/images/手作鬆餅.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (505, N'炸物拼盤', N'RAINBOWPAPA', 100, N'../productInfo/images/炸物拼盤.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (507, N'芒果蛋糕', N'RAINBOWPAPA', 110, N'../productInfo/images/芒果蛋糕.jpg', 0, 20)
INSERT [dbo].[ProductInfo] ([product_id], [product_name], [product_shop], [product_price], [product_picture], [product_quantity], [Store_Id]) VALUES (506, N'酥皮濃湯', N'RAINBOWPAPA', 90, N'../productInfo/images/酥皮濃湯.jpg', 0, 20)
GO
SET IDENTITY_INSERT [dbo].[Store] ON 

INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (1, N'燒肉同話', N'燒肉', N'桃園市桃園區中正路1003號1樓', N'85度C旗下新品牌-燒肉同話近期非常夯

採夜店酒吧風的裝潢設計，還有超熱情的專業服務

加上精緻頂級的燒肉食材，享受美食同時也能享受快樂的氛圍
燒肉套餐，肉質鮮嫩且份量十足
非常適合三五好友一同來聊天聚會同歡啦', N'03-3583311', N'Images/Chagether-banner.jpg', N'Images/燒肉同話.jpg', N'85度C旗下新品牌-燒肉同話近期非常夯

採夜店酒吧風的裝潢設計，還有超熱情的專業服務

加上精緻頂級的燒肉食材，享受美食同時也能享受快樂的氛圍
燒肉套餐，肉質鮮嫩且份量十足
非常適合三五好友一同來聊天聚會同歡啦', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (2, N'鬥佐燒肉', N'燒肉', N'桃園市桃園區大興西路二段8號', N'一般燒肉吃到飽的餐廳

都會因為成本考量

使用組合肉或是比較低價的肉

所以吃起來都會乾乾柴柴

或是使用一堆醬料去醃製

讓你吃不出肉的品質！

但，今天布咕先生要來跟大家介紹這家燒肉吃到飽

他們都是百分之百使用原肉

而且都是單點餐廳的高等級

品質不馬虎

更有比１０元大的干貝和生蠔哦～！', N'03-3558680', N'Images/2019_05_16_banner_e2e56f078e.jpg', N'Images/鬥佐燒肉11.jpg', N'一般燒肉吃到飽的餐廳

都會因為成本考量

使用組合肉或是比較低價的肉

所以吃起來都會乾乾柴柴

或是使用一堆醬料去醃製

讓你吃不出肉的品質！

但，今天布咕先生要來跟大家介紹這家燒肉吃到飽

他們都是百分之百使用原肉

而且都是單點餐廳的高等級

品質不馬虎

更有比１０元大的干貝和生蠔哦～！', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (3, N'森日式料理', N'日式', N'桃園市桃園區經國路336巷86號', N'一般的餐廳，都是把店內的環境打造成有如當地用餐的氣氛、氛圍，但往往忽略了店外觀以及餐點的美味，而森日式料理，他可是店家耗資４０００萬元打造一座日本式庭園景觀的餐廳。
營業時間：早上11:30~14:30，17:00~22:00
營業時間：早上11:30~14:30，17:00~22:00', N'03-4532459', N'Images/森日式料理banner.jpg', N'Images/IMG_4174.jpg', N'一般的餐廳，都是把店內的環境打造成有如當地用餐的氣氛、氛圍，但往往忽略了店外觀以及餐點的美味，而森日式料理，他可是店家耗資４０００萬元打造一座日本式庭園景觀的餐廳。
營業時間：早上11:30~14:30，17:00~22:00
營業時間：早上11:30~14:30，17:00~22:00', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (4, N'瑞記海南雞飯', N'中式', N'桃園市中壢區中園路二段501號(大江國際購物中心GB樓東側G026店號)', N'每日產地直送新鮮溫體雞，以低溫烹煮方法達到極致口感。十年經驗老牌手法獨家去骨處理，保證啖啖鮮雞肉。只使用當日新鮮食材，絕不過夜。搭配三款秘製醬汁以及兩款獨特口味雞油飯。
營業時間:11:00~22:00(週日到週四)，11:00~22:30(週五~週六，例假日前一天)', N'03-4680689', N'Images/banner_page-0001.jpg', N'Images/1538116798-1112015215_l.jpg', N'每日產地直送新鮮溫體雞，以低溫烹煮方法達到極致口感。十年經驗老牌手法獨家去骨處理，保證啖啖鮮雞肉。只使用當日新鮮食材，絕不過夜。搭配三款秘製醬汁以及兩款獨特口味雞油飯。
營業時間:11:00~22:00(週日到週四)，11:00~22:30(週五~週六，例假日前一天)', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (5, N'地平線1號', N'快餐', N'桃園市龜山區復興一路212巷25號', N'位於桃園龜山林口長庚醫院附近的巷弄裡
新開了一間早午餐與異國料理的餐廳
裡面有著舒適的用餐環境以及美味餐點
是享受悠閒時光的好地方
營業時間：早上10:00~22:00', N'03-4591479', N'Images/地平線一號5.jpg', N'Images/地平線1號.jpg', N'位於桃園龜山林口長庚醫院附近的巷弄裡
新開了一間早午餐與異國料理的餐廳
裡面有著舒適的用餐環境以及美味餐點
是享受悠閒時光的好地方
營業時間：早上10:00~22:00', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (6, N'Woosaパンケーキ 屋莎鬆餅屋 桃園三越站前店', N'下午茶', N'桃園市桃園區中正路19號3樓(桃園新光三越站前店)', N'有藍帶級「雲の鬆餅」之稱的「屋莎鬆餅屋」，不僅一開店就深受歡迎，人氣也越來越旺啦!「屋莎鬆餅屋」桃園三越站前店不僅用餐環境溫馨又舒適，餐點選擇多元，尤其招牌雲の鬆餅、雲の優格、堪稱邪惡等級的美味啊!', N'03-3327686', N'Images/1574669724-4147325432banner.jpg', N'Images/3_79.jpg', N'有藍帶級「雲の鬆餅」之稱的「屋莎鬆餅屋」，不僅一開店就深受歡迎，人氣也越來越旺啦!「屋莎鬆餅屋」桃園三越站前店不僅用餐環境溫馨又舒適，餐點選擇多元，尤其招牌雲の鬆餅、雲の優格、堪稱邪惡等級的美味啊!', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (7, N'熱浪島', N'西式', N'台中市南屯區向上路三段 536 號', N'熱浪島 以特殊南洋風味料理的⽅式來推廣素食，選擇符合大眾的口味料理，不只麵、飯、火鍋、茶點選擇多且好吃、份量飽足、又創意，希望能夠吸引更多年輕族群加入蔬食行列。熱浪島 套餐、鍋物皆附飲料四選一、甜點三選一或是折50元換購飲品
營業時間：11:00–21:00', N'04 2380 1133', N'Images/169a06521c9336e859d5c925c514b265熱浪BANNER.jpg', N'Images/79a9bff0-8aab-11ea-bb37-665344352c09熱浪島.jpg', N'熱浪島 以特殊南洋風味料理的⽅式來推廣素食，選擇符合大眾的口味料理，不只麵、飯、火鍋、茶點選擇多且好吃、份量飽足、又創意，希望能夠吸引更多年輕族群加入蔬食行列。熱浪島 套餐、鍋物皆附飲料四選一、甜點三選一或是折50元換購飲品
營業時間：11:00–21:00', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (8, N'瓦城', N'西式', N'台北市內湖區成功路四段188號2樓', N'瓦城是台灣最知名的泰國料理品牌，在現在泰式料理大小家餐廳的競爭下，每到周末還是坐無虛席，就連我們家也都是他們的常客，每一樣都做得更得美味是他們的特色！
 (服務時間：週一~五9:30~18:00)', N'0800-086-680', N'Images/1561021689-87fa104e205e3d642206724b31b4be95BAN.jpg', N'Images/瓦城載.jpg', N'瓦城是台灣最知名的泰國料理品牌，在現在泰式料理大小家餐廳的競爭下，每到周末還是坐無虛席，就連我們家也都是他們的常客，每一樣都做得更得美味是他們的特色！
 (服務時間：週一~五9:30~18:00)', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (9, N'大腕燒肉', N'燒肉', N'106台北市大安區敦化南路一段177巷22號1樓', N'獲選台北四大燒肉、全台頂級十大燒烤店NO.1的「大腕燒肉」，2019繼續蟬聯米其林一星寶座，也是唯一摘星的燒肉餐廳。
營業時間：週日至週四 18:00~00:00（週五六~02:00）

', N' 02 2711 0179', N'Images/大腕燒肉banner.jpg', N'Images/大腕燒肉.jpg', N'獲選台北四大燒肉、全台頂級十大燒烤店NO.1的「大腕燒肉」，2019繼續蟬聯米其林一星寶座，也是唯一摘星的燒肉餐廳。
營業時間：週日至週四 18:00~00:00（週五六~02:00）

', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (10, N'牧水巷老爺製作所', N'下午茶', N'台南市中西區開山路35巷1弄6號', N'巷弄美食再來一間～台南真的是鑽進小巷裏，處處有驚喜！這棟在紅磚圍牆裏的老屋，是一間靜謐、輕鬆、悠閒的咖啡店，如果想遠離人車的喧囂，那麼到這裏絕對沒有錯，用「牧水巷」的一份甜點、一杯咖啡，陪你輕鬆度過下午茶時光～
營業時間：13:00-20:00
公休日：每週一', N'0906142995', N'Images/20200207180238_30.jpg', N'Images/SA.jpg', N'巷弄美食再來一間～台南真的是鑽進小巷裏，處處有驚喜！這棟在紅磚圍牆裏的老屋，是一間靜謐、輕鬆、悠閒的咖啡店，如果想遠離人車的喧囂，那麼到這裏絕對沒有錯，用「牧水巷」的一份甜點、一杯咖啡，陪你輕鬆度過下午茶時光～
營業時間：13:00-20:00
公休日：每週一', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (11, N'螺絲瑪莉', N'西式', N'台北市中山區南京西路12巷13弄9號', N'來Rosemary《螺(蘿)絲瑪莉義麵坊》用餐，餐前我們會招待您一份手工麵包，您只要有點主餐，餐後Rosemary也會招待您我們自己親手做的甜點喔！Rosemary會用最精緻的餐點、最親切的服務，與您一同共享美好的用餐時刻。營業時間	AM 11:30 ~ PM 14:30（16:00 休息）
PM 17:30 ~ PM 20:30（22:00 打烊）', N'(02)2521-9822', N'Images/螺絲瑪莉banner.jpg', N'Images/螺絲瑪莉.jpg', N'來Rosemary《螺(蘿)絲瑪莉義麵坊》用餐，餐前我們會招待您一份手工麵包，您只要有點主餐，餐後Rosemary也會招待您我們自己親手做的甜點喔！Rosemary會用最精緻的餐點、最親切的服務，與您一同共享美好的用餐時刻。營業時間	AM 11:30 ~ PM 14:30（16:00 休息）
PM 17:30 ~ PM 20:30（22:00 打烊）', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (12, N'Morning Burger茉莉漢堡', N'快餐', N'桃園區桃園市同德十街28號', N'每天為您現做漢堡、三明治、歐姆蕾捲及精心製作早午餐點。
早餐也可以成為您樂活的一種享受，在這裡請盡情展現您悠閒的姿態!
MORNING BURGER”以客為尊”的創店初衷，將傳統早餐店注入新元素；低價消費享受美食的同時亦能感受熱情服務是我們的經營理念。
', N'03-215 1538', N'Images/Morning Burger茉莉漢堡banner.jpp.jpg', N'Images/Morning Burger茉莉漢堡.jpeg', N'每天為您現做漢堡、三明治、歐姆蕾捲及精心製作早午餐點。
早餐也可以成為您樂活的一種享受，在這裡請盡情展現您悠閒的姿態!
MORNING BURGER”以客為尊”的創店初衷，將傳統早餐店注入新元素；低價消費享受美食的同時亦能感受熱情服務是我們的經營理念。
', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (14, N'教父牛排 Danny''s Steakhouse', N'西式', N'台北市中山區樂群三路58號', N'教父牛排 Danny''s Steak House 品牌故事

以教父級品質保證之意而命名的「教父牛排」

是鄧有癸可以真正從理念到餐點皆能符合自己要求的牛排館

怎樣能做出一塊更好的牛排則是我一直在努力追求的」。', N'02-85011838', N'Images/教父牛排 Dbanner.jpg', N'Images/教父牛排PHOTO.jpg', N'教父牛排 Danny''s Steak House 品牌故事

以教父級品質保證之意而命名的「教父牛排」

是鄧有癸可以真正從理念到餐點皆能符合自己要求的牛排館

怎樣能做出一塊更好的牛排則是我一直在努力追求的」。', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (15, N'豪哥海鮮熱炒', N'中式', N'桃園市中埔六街81號', N'店內環境

簡簡淨淨、明亮明亮

還有電視、冷氣、電風扇

很適合家庭聚餐
價格來說，相當的平價

整餐吃下來我們二個人才花320元

而且味道也不差

有機會可以來吃吃看~~!!
營業時間：週一-週日:11:00 - 21:30', N'0925151692', N'Images/豪哥快炒BANNER.jpg', N'Images/1605013642-1876735125-g_l.jpg', N'店內環境

簡簡淨淨、明亮明亮

還有電視、冷氣、電風扇

很適合家庭聚餐
價格來說，相當的平價

整餐吃下來我們二個人才花320元

而且味道也不差

有機會可以來吃吃看~~!!
營業時間：週一-週日:11:00 - 21:30', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (16, N'小東門蒸烤鮮飯食', N'中式', N'新竹市東區大同路86號 (東門市場1082室)', N'人氣名店"小東門-蒸烤鮮飯食"。
結合了日本、台灣及粵式料理，是學生、上班族消夜聚餐的最佳選擇！
營業時間： 17:00~23:00', N'03-5237883', N'Images/小東門。蒸烤鮮飯食banner.jpg', N'Images/小東門蒸烤鮮飯食.jpg', N'人氣名店"小東門-蒸烤鮮飯食"。
結合了日本、台灣及粵式料理，是學生、上班族消夜聚餐的最佳選擇！
營業時間： 17:00~23:00', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (17, N'靜岡勝政豬排', N'日式', N'桃園市中壢區中園路二段501號GBF', N'於內甜美細緻又多汁」的完美之境！是靜岡勝政豬排的使命！ 位於富士山腳下的靜岡勝政日式豬排，為日本靜岡縣屹立近20年的指標性排隊豬排名店，是當地人氣不墜的豬排名店。為呈現最原汁原味的靜岡勝政日式豬排風味饗宴，從食材、製作、食器選擇、服務，無一不細心嚴選打造，以期完整體現靜岡勝政日式豬排不斷追求「究極的日式豬排」的精神。', N'034680000', N'Images/靜岡勝政豬排23.jpg', N'Images/靜岡勝政豬排20181228_181827.jpg', N'於內甜美細緻又多汁」的完美之境！是靜岡勝政豬排的使命！ 位於富士山腳下的靜岡勝政日式豬排，為日本靜岡縣屹立近20年的指標性排隊豬排名店，是當地人氣不墜的豬排名店。為呈現最原汁原味的靜岡勝政日式豬排風味饗宴，從食材、製作、食器選擇、服務，無一不細心嚴選打造，以期完整體現靜岡勝政日式豬排不斷追求「究極的日式豬排」的精神。', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (18, N'拉麵吧RamanBar', N'日式', N'桃園市中壢區大同路84號', N'說起日本美食就讓人想起串燒和拉麵，拉麵吧結合日本的美食代表，推出雞肉串燒拉麵，提供多種湯頭讓饕客依偏好選擇，讓來拉麵吧客人們花少少錢就可以品嚐到日本的代表美食。', N'03-4220111', N'Images/拉麵吧banner.jpg', N'Images/拉麵吧3.JPG', N'說起日本美食就讓人想起串燒和拉麵，拉麵吧結合日本的美食代表，推出雞肉串燒拉麵，提供多種湯頭讓饕客依偏好選擇，讓來拉麵吧客人們花少少錢就可以品嚐到日本的代表美食。', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (19, N'硬派主廚的軟嫩料理', N'燒肉', N'新竹市東區中央路102巷10號300(東門市場 1143號', N'選用USDA Choice美國特選級牛肉，採用低溫熟成的舒服料理技法製作，保留了每塊肉品的原汁原味，再將表皮炙燒到微焦，一口咬下軟嫩的口感，搭配季節時蔬的享用，拌入半熟溫泉蛋、古早味肉燥飯，整體料理不過度烹調反而保留了食材的真實味道，肉肉控的必點料理～', N'03-5251323', N'Images/硬派主廚banner.JPG', N'Images/硬派主廚.jpg', N'選用USDA Choice美國特選級牛肉，採用低溫熟成的舒服料理技法製作，保留了每塊肉品的原汁原味，再將表皮炙燒到微焦，一口咬下軟嫩的口感，搭配季節時蔬的享用，拌入半熟溫泉蛋、古早味肉燥飯，整體料理不過度烹調反而保留了食材的真實味道，肉肉控的必點料理～', NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (20, N'RAINBOWPAPA', N'下午茶', N'桃園市中壢區幸福新村3號', N'每週都供應不同的主廚特餐，讓饕客每次來都有不一樣的味覺感受與驚喜，走進RAINBOW PAPA，感受全新的幸福滋味。', N'03-4655377', N'Images/RAINBOWPAPAbanner.JPG', N'Images/RAINBOWPAPAphoto.JPG', NULL, NULL)
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl], [stitddt], [price]) VALUES (40, N'測試用12', N'中式', N'測試用', N'', N'測試用', N'Images/04.jpg', N'Images/10230948.png', N'測試用', NULL)
SET IDENTITY_INSERT [dbo].[Store] OFF
GO
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES (N'W', N'女性')
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES (N'N', N'不方便提供')
INSERT [dbo].[UserGender] ([genderCode], [genderText]) VALUES (N'M', N'男性')
GO
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (1, N'店家')
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (0, N'消費者')
INSERT [dbo].[UserLevel] ([lv], [levelName]) VALUES (-1, N'管理員')
GO
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'1000001', N'George017', N'George017', N'曾', N'丙顥', N'阿丙', N'M', CAST(N'1992-01-16' AS Date), N'快餐,西式', N'george610787@outlook.com', N'0911954504', N'Y', N't03', CAST(N'2020-11-20' AS Date), 0, N'桃園市平鎮區祥安街6巷10號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'1000002', N'George992002017', N'Phy017George', N'曾', N'世宏', N'世宏', N'M', CAST(N'1992-01-16' AS Date), N'中式.燒肉,日式', N'st2004116002@yhaoo.com.tw', N'034690972', N'Y', N't03', CAST(N'2020-11-20' AS Date), 0, N'桃園市龍潭區中興路392巷62號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'1000003', N'George610787', N'Geo1rge6', N'曾', N'繁茂', N'繁茂', N'M', CAST(N'1959-02-16' AS Date), N'中式,日式,皆可', N'george610787@gmail.com', N'034799435', N'N', N't03', CAST(N'2020-11-20' AS Date), 0, N'桃園市平鎮區中大路300號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'1000004', N'TomCat2266', N'TomCat2266', N'吳', N'承恩', N'小吳', N'M', CAST(N'2020-12-02' AS Date), N'米食,快餐,燒肉,西式,下午茶,日式,皆可', N'mp4056@yahoo.com.tw', N'0931690968', N'Y', N't05', CAST(N'2020-11-24' AS Date), 0, N'安平區海安路二段22號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'2000001', N'ComeBackMac', N'MC20201127', N'王', N'小明', N'阿明', N'M', CAST(N'2000-10-10' AS Date), N'快餐,西式', N'mac@gmail.com', N'0933552266', N'N', N't03', CAST(N'2020-11-27' AS Date), 1, N'桃園市中壢區中正路93號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
INSERT [dbo].[WebUser] ([user_id], [account], [password], [first_name], [last_name], [nickname], [gender], [birth], [fervor], [email], [phone], [get_email], [location_code], [join_date], [lv], [addr0], [addr1], [addr2], [zest], [version]) VALUES (N'2000002', N'LoveNoodles', N'LVN1127tw', N'蔣', N'幹', N'我不會畫', N'M', CAST(N'1998-04-01' AS Date), N'中式', N'lovenoodles@outlook.com', N'0977889966', N'N', N't02', CAST(N'2020-11-27' AS Date), 1, N'新北市板橋區連城路469巷79弄1號', N'', N'', CAST(0.00 AS Decimal(10, 2)), 0)
GO
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'0000001', N'George610787', N'桃園市平鎮區祥安街6巷10號', N'', N'', CAST(N'1991-12-12' AS Date), N'georgecycuphy@cycu.org.tw', N'中式,燒肉,日式,皆可', N'曾', N'M', N'N', CAST(N'2020-12-08' AS Date), N'世宏', N't03', -1, N'世宏', N'Geo1rge6', N'034690972', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000001', N'George017', N'桃園市平鎮區中大路300號', N'', N'', CAST(N'1992-01-16' AS Date), N'george610787@outlook.com', N'中式', N'曾', N'M', N'Y', CAST(N'2020-12-01' AS Date), N'丙顥', N't03', 0, N'阿丙', N'Geo1rge6', N'0911954504', N'active', 3, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000002', N'WangOldK', N'新竹市中正路120號', N'', N'', CAST(N'2000-10-10' AS Date), N'WangOldK@outlook.com', N'燒肉,西式,日式', N'王', N'M', N'N', CAST(N'2020-12-01' AS Date), N'老千', N't08', 0, N'老千', N'Geo1rge6', N'0911993377', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000003', N'George000', N'新北市板橋區中山路一段161號', N'', N'', CAST(N'1995-12-25' AS Date), N'George000@outlook.com', N'快餐,西式', N'吳', N'M', N'Y', CAST(N'2020-12-02' AS Date), N'此仁', N't02', 0, N'阿仁', N'Geo1rge6', N'0933778822', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000004', N'TomCat2266', N'安平區海安路二段22號', N'', N'', CAST(N'2020-12-02' AS Date), N'mp405@gmail.com', N'中式,快餐,燒肉,西式,下午茶,日式,皆可', N'吳', N'M', N'Y', CAST(N'2020-12-02' AS Date), N'承恩', N't05', 0, N'小吳', N'TomCat2266', N'0931690968', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000005', N'AndroidT800', N'高雄市苓雅區自強三路3號', N'', N'', CAST(N'1990-11-11' AS Date), N'AndroidT800@yahoo.com', N'快餐,西式,下午茶', N'李', N'N', N'N', CAST(N'2020-12-02' AS Date), N'麥克', N't06', 0, N'麥克', N'AndroidT800', N'0933669900', N'quit', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000006', N'AndroidX24', N'彰化縣三民路1號', N'', N'', CAST(N'1995-08-21' AS Date), N'AndroidX24@yahoo.com', N'皆可', N'藍', N'F', N'N', CAST(N'2020-12-02' AS Date), N'澤光', N't12', 0, N'小光', N'AndroidX24', N'0922446688', N'quit', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'1000007', N'Robert000', N'南投縣埔里鎮中山路一段421號', N'', N'', CAST(N'2003-08-02' AS Date), N'Robert000@yahoo.com', N'皆可', N'羅', N'N', N'N', CAST(N'2020-12-04' AS Date), N'伯特', N't13', 0, N'機器人', N'Robert000', N'0955775533', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'2000001', N'ComeBackMac', N'桃園市中壢區中正路93號', N'', N'', CAST(N'2000-10-10' AS Date), N'mac@gmail.com', N'快餐,西式,皆可', N'王', N'M', N'N', CAST(N'2020-12-02' AS Date), N'小明', N't03', 1, N'阿明', N'MC20201127', N'0933552266', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
INSERT [dbo].[WebUserData] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [gender], [getEmail], [joinDate], [lastName], [locationCode], [lv], [nickname], [password], [phone], [status], [version], [zest]) VALUES (N'2000002', N'LoveNoodles', N'新北市板橋區連城路469巷79弄1號', N'', N'', CAST(N'1998-04-01' AS Date), N'lovenoodle@outllok.com', N'中式', N'蔣', N'M', N'N', CAST(N'2020-12-02' AS Date), N'幹', N't02', 1, N'我不會畫', N'LVN1127tw', N'0977889966', N'active', 0, CAST(0.00 AS Decimal(19, 2)))
GO
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES (N'0000000', N'WebAdmin', N'桃園市中壢區中大路300號', N'', N'', CAST(N'1990-01-01' AS Date), N'notARealEmail@gmail.com', N'皆可', N'管', CAST(N'2020-11-04' AS Date), N'李元', N'管理員', N'WebAdmin2020', N'0955779933', N'active', 0, CAST(0.00 AS Decimal(19, 2)), -1, N'N', N'N', 3, N'')
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES (N'1000000', N'TestUser', N'桃園市中壢區中大路300號', N'', N'', CAST(N'2000-10-10' AS Date), N'notARealEmail@yahoo.com', N'中式,快餐,燒肉,西式,下午茶,日式,皆可', N'吳', CAST(N'2020-11-04' AS Date), N'此仁', N'無此人', N'TestUser1256', N'0911223344', N'active', 0, CAST(0.00 AS Decimal(19, 2)), 0, N'M', N'N', 3, N'')
INSERT [dbo].[WebUserInfo] ([userId], [account], [addr0], [addr1], [addr2], [birth], [email], [fervor], [firstName], [joinDate], [lastName], [nickname], [password], [phone], [status], [version], [zest], [lv], [genderCode], [willingCode], [cityCode], [iconUrl]) VALUES (N'2000000', N'TestBoss', N'桃園市中壢區中大路300號', N'', N'', CAST(N'1980-12-31' AS Date), N'notARealEmail@outlook.com', N'中式,快餐,燒肉', N'郝', CAST(N'2020-11-04' AS Date), N'頭家', N'好頭家', N'TestBossZest1104', N'0933441122', N'active', 0, CAST(0.00 AS Decimal(19, 2)), 1, N'W', N'N', 3, N'')
GO
INSERT [dbo].[Willing] ([willingCode], [willingText]) VALUES (N'N', N'不願意')
INSERT [dbo].[Willing] ([willingCode], [willingText]) VALUES (N'Y', N'願意')
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_ksjjchxexvlo4dtdk9wjr82xj]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[CityCode] ADD  CONSTRAINT [UK_ksjjchxexvlo4dtdk9wjr82xj] UNIQUE NONCLUSTERED 
(
	[cityName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_p695dh4ppy0jphhjyam6956i7]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[CityInfo] ADD  CONSTRAINT [UK_p695dh4ppy0jphhjyam6956i7] UNIQUE NONCLUSTERED 
(
	[cityName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_iiex61eamu4ta2erskcih7kgd]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[FoodFervor] ADD  CONSTRAINT [UK_iiex61eamu4ta2erskcih7kgd] UNIQUE NONCLUSTERED 
(
	[fervorItem] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_6biikl6ls93a5yn0etlutdwh4]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[UserGender] ADD  CONSTRAINT [UK_6biikl6ls93a5yn0etlutdwh4] UNIQUE NONCLUSTERED 
(
	[genderText] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_ik07g837b0iwgrx8ysh1n55dr]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[UserLevel] ADD  CONSTRAINT [UK_ik07g837b0iwgrx8ysh1n55dr] UNIQUE NONCLUSTERED 
(
	[levelName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_9amedxnyr5jnnnqo9we5ovwg3]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserData] ADD  CONSTRAINT [UK_9amedxnyr5jnnnqo9we5ovwg3] UNIQUE NONCLUSTERED 
(
	[nickname] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_h17l934knbqt08iv77pe43q4p]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserData] ADD  CONSTRAINT [UK_h17l934knbqt08iv77pe43q4p] UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_lj1392v7u534y15harhrfs8h]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserData] ADD  CONSTRAINT [UK_lj1392v7u534y15harhrfs8h] UNIQUE NONCLUSTERED 
(
	[account] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_psv6w2bnd52v20c7mx2xonwfg]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserData] ADD  CONSTRAINT [UK_psv6w2bnd52v20c7mx2xonwfg] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_5ocfi6r1ctle3y5bwtg499p7k]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserInfo] ADD  CONSTRAINT [UK_5ocfi6r1ctle3y5bwtg499p7k] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_6pdk3m726lwpqbu9qndlgiiiq]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserInfo] ADD  CONSTRAINT [UK_6pdk3m726lwpqbu9qndlgiiiq] UNIQUE NONCLUSTERED 
(
	[account] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_9dtgrpqamnpuyee2l3y64parp]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserInfo] ADD  CONSTRAINT [UK_9dtgrpqamnpuyee2l3y64parp] UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_mobvf12a6ydimn68jas1w23gg]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[WebUserInfo] ADD  CONSTRAINT [UK_mobvf12a6ydimn68jas1w23gg] UNIQUE NONCLUSTERED 
(
	[nickname] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_c7sigoqba1yry9fus7gpk01t1]    Script Date: 2020/12/18 下午 09:12:21 ******/
ALTER TABLE [dbo].[Willing] ADD  CONSTRAINT [UK_c7sigoqba1yry9fus7gpk01t1] UNIQUE NONCLUSTERED 
(
	[willingText] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[AreaCode] ADD  DEFAULT ((0)) FOR [areaCode]
GO
ALTER TABLE [dbo].[UserLevel] ADD  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[WebUser] ADD  DEFAULT ('N') FOR [gender]
GO
ALTER TABLE [dbo].[WebUser] ADD  DEFAULT ('Y') FOR [get_email]
GO
ALTER TABLE [dbo].[WebUser] ADD  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[WebUser] ADD  DEFAULT ((0)) FOR [zest]
GO
ALTER TABLE [dbo].[WebUser] ADD  DEFAULT ((0)) FOR [version]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ('') FOR [addr1]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ('') FOR [addr2]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ('inactive') FOR [status]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ((0)) FOR [version]
GO
ALTER TABLE [dbo].[WebUserData] ADD  DEFAULT ((0.00)) FOR [zest]
GO
ALTER TABLE [dbo].[WebUserDeleted] ADD  DEFAULT ('N') FOR [gender]
GO
ALTER TABLE [dbo].[WebUserDeleted] ADD  DEFAULT ('Y') FOR [get_email]
GO
ALTER TABLE [dbo].[WebUserDeleted] ADD  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[WebUserDeleted] ADD  DEFAULT ((0)) FOR [zest]
GO
ALTER TABLE [dbo].[WebUserDeleted] ADD  DEFAULT ((0)) FOR [version]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ('') FOR [addr1]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ('') FOR [addr2]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ('inactive') FOR [status]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ((0)) FOR [version]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ((0.00)) FOR [zest]
GO
ALTER TABLE [dbo].[WebUserInfo] ADD  DEFAULT ((0)) FOR [lv]
GO
ALTER TABLE [dbo].[AreaCode]  WITH CHECK ADD  CONSTRAINT [FKabtr0tt4f2qq7w07ep6nxyrk4] FOREIGN KEY([cityCode])
REFERENCES [dbo].[CityCode] ([cityCode])
GO
ALTER TABLE [dbo].[AreaCode] CHECK CONSTRAINT [FKabtr0tt4f2qq7w07ep6nxyrk4]
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
ALTER TABLE [dbo].[WebUserInfo]  WITH CHECK ADD  CONSTRAINT [FK123oqqxpspxl5m7nekk6u65ki] FOREIGN KEY([genderCode])
REFERENCES [dbo].[UserGender] ([genderCode])
GO
ALTER TABLE [dbo].[WebUserInfo] CHECK CONSTRAINT [FK123oqqxpspxl5m7nekk6u65ki]
GO
ALTER TABLE [dbo].[WebUserInfo]  WITH CHECK ADD  CONSTRAINT [FK3lwps3pr2rfy5r03r3p1ju9va] FOREIGN KEY([willingCode])
REFERENCES [dbo].[Willing] ([willingCode])
GO
ALTER TABLE [dbo].[WebUserInfo] CHECK CONSTRAINT [FK3lwps3pr2rfy5r03r3p1ju9va]
GO
ALTER TABLE [dbo].[WebUserInfo]  WITH CHECK ADD  CONSTRAINT [FK5djstrq4w5q6ubvh6k74t9jwf] FOREIGN KEY([lv])
REFERENCES [dbo].[UserLevel] ([lv])
GO
ALTER TABLE [dbo].[WebUserInfo] CHECK CONSTRAINT [FK5djstrq4w5q6ubvh6k74t9jwf]
GO
ALTER TABLE [dbo].[WebUserInfo]  WITH CHECK ADD  CONSTRAINT [FKdcd8opbjsd20c57uh0xm7j6l2] FOREIGN KEY([cityCode])
REFERENCES [dbo].[CityInfo] ([cityCode])
GO
ALTER TABLE [dbo].[WebUserInfo] CHECK CONSTRAINT [FKdcd8opbjsd20c57uh0xm7j6l2]
GO
USE [master]
GO
ALTER DATABASE [WebProject] SET  READ_WRITE 
GO
