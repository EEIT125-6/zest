
/****** Object:  Table [dbo].[Store]    Script Date: 2020/11/23 下午 05:47:40 ******/
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
	[photourl] [nvarchar](100) NULL
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Store] ON 
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (1, N'燒肉同話', N'燒肉', N'桃園市桃園區中正路1003號1樓', N'85度C旗下新品牌-燒肉同話近期非常夯

採夜店酒吧風的裝潢設計，還有超熱情的專業服務

加上精緻頂級的燒肉食材，享受美食同時也能享受快樂的氛圍
燒肉套餐，肉質鮮嫩且份量十足
非常適合三五好友一同來聊天聚會同歡啦', N'03-3583311', N'Images/Chagether-banner.jpg', N'Images/燒肉同話123.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (10, N'牧水巷老爺製作所', N'下午茶', N'台南市中西區開山路35巷1弄6號', N'巷弄美食再來一間～台南真的是鑽進小巷裏，處處有驚喜！這棟在紅磚圍牆裏的老屋，是一間靜謐、輕鬆、悠閒的咖啡店，如果想遠離人車的喧囂，那麼到這裏絕對沒有錯，用「牧水巷」的一份甜點、一杯咖啡，陪你輕鬆度過下午茶時光～
營業時間：13:00-20:00
公休日：每週一', N'0906142995', N'Images/20200207180238_30.jpg', N'Images/SA.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (2, N'鬥佐燒肉', N'燒肉', N'桃園市桃園區大興西路二段8號', N'一般燒肉吃到飽的餐廳

都會因為成本考量

使用組合肉或是比較低價的肉

所以吃起來都會乾乾柴柴

或是使用一堆醬料去醃製

讓你吃不出肉的品質！

但，今天布咕先生要來跟大家介紹這家燒肉吃到飽

他們都是百分之百使用原肉

而且都是單點餐廳的高等級

品質不馬虎

更有比１０元大的干貝和生蠔哦～！', N'03-3558680', N'Images/2019_05_16_banner_e2e56f078e.jpg', N'Images/鬥佐燒肉11.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (3, N'森日式料理', N'日式', N'桃園市桃園區經國路336巷86號', N'一般的餐廳，都是把店內的環境打造成有如當地用餐的氣氛、氛圍，但往往忽略了店外觀以及餐點的美味，而森日式料理，他可是店家耗資４０００萬元打造一座日本式庭園景觀的餐廳。
營業時間：早上11:30~14:30，17:00~22:00
營業時間：早上11:30~14:30，17:00~22:00', N'03-4532459', N'Images/森日式料理banner.jpg', N'Images/IMG_4174.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (4, N'瑞記海南雞飯', N'中式', N'桃園市中壢區中園路二段501號(大江國際購物中心GB樓東側G026店號)', N'每日產地直送新鮮溫體雞，以低溫烹煮方法達到極致口感。十年經驗老牌手法獨家去骨處理，保證啖啖鮮雞肉。只使用當日新鮮食材，絕不過夜。搭配三款秘製醬汁以及兩款獨特口味雞油飯。
營業時間:11:00~22:00(週日到週四)，11:00~22:30(週五~週六，例假日前一天)', N'03-4680689', N'Images/banner_page-0001.jpg', N'Images/1538116798-1112015215_l.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (5, N'地平線1號', N'快餐', N'桃園市龜山區復興一路212巷25號', N'位於桃園龜山林口長庚醫院附近的巷弄裡
新開了一間早午餐與異國料理的餐廳
裡面有著舒適的用餐環境以及美味餐點
是享受悠閒時光的好地方
營業時間：早上10:00~22:00', N'03-4591479', N'Images/地平線一號5.jpg', N'Images/地平線1號.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (6, N'Woosaパンケーキ 屋莎鬆餅屋 桃園三越站前店', N'下午茶', N'桃園市桃園區中正路19號3樓(桃園新光三越站前店)', N'有藍帶級「雲の鬆餅」之稱的「屋莎鬆餅屋」，不僅一開店就深受歡迎，人氣也越來越旺啦!「屋莎鬆餅屋」桃園三越站前店不僅用餐環境溫馨又舒適，餐點選擇多元，尤其招牌雲の鬆餅、雲の優格、堪稱邪惡等級的美味啊!', N'03-3327686', N'Images/1574669724-4147325432banner.jpg', N'Images/3_79.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (7, N'熱浪島', N'西式', N'台中市南屯區向上路三段 536 號', N'熱浪島 以特殊南洋風味料理的⽅式來推廣素食，選擇符合大眾的口味料理，不只麵、飯、火鍋、茶點選擇多且好吃、份量飽足、又創意，希望能夠吸引更多年輕族群加入蔬食行列。熱浪島 套餐、鍋物皆附飲料四選一、甜點三選一或是折50元換購飲品
營業時間：11:00–21:00', N'04 2380 1133', N'Images/169a06521c9336e859d5c925c514b265熱浪BANNER.jpg', N'Images/79a9bff0-8aab-11ea-bb37-665344352c09熱浪島.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (8, N'瓦城', N'西式', N'台北市內湖區成功路四段188號2樓', N'瓦城是台灣最知名的泰國料理品牌，在現在泰式料理大小家餐廳的競爭下，每到周末還是坐無虛席，就連我們家也都是他們的常客，每一樣都做得更得美味是他們的特色！
 (服務時間：週一~五9:30~18:00)', N'0800-086-680', N'Images/1561021689-87fa104e205e3d642206724b31b4be95BAN.jpg', N'Images/瓦城載.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (9, N'大腕燒肉', N'燒肉', N'106台北市大安區敦化南路一段177巷22號1樓', N'獲選台北四大燒肉、全台頂級十大燒烤店NO.1的「大腕燒肉」，2019繼續蟬聯米其林一星寶座，也是唯一摘星的燒肉餐廳。
營業時間：週日至週四 18:00~00:00（週五六~02:00）

', N' 02 2711 0179', N'Images/大腕燒肉banner.jpg', N'Images/大腕燒肉.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (11, N'螺絲瑪莉', N'西式', N'台北市中山區南京西路12巷13弄9號', N'來Rosemary《螺(蘿)絲瑪莉義麵坊》用餐，餐前我們會招待您一份手工麵包，您只要有點主餐，餐後Rosemary也會招待您我們自己親手做的甜點喔！Rosemary會用最精緻的餐點、最親切的服務，與您一同共享美好的用餐時刻。營業時間	AM 11:30 ~ PM 14:30（16:00 休息）
PM 17:30 ~ PM 20:30（22:00 打烊）', N'(02)2521-9822', N'Images/螺絲瑪莉banner.jpg', N'Images/螺絲瑪莉.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (12, N'Morning Burger茉莉漢堡', N'快餐', N'桃園區桃園市同德十街28號', N'每天為您現做漢堡、三明治、歐姆蕾捲及精心製作早午餐點。
早餐也可以成為您樂活的一種享受，在這裡請盡情展現您悠閒的姿態!
MORNING BURGER”以客為尊”的創店初衷，將傳統早餐店注入新元素；低價消費享受美食的同時亦能感受熱情服務是我們的經營理念。
', N'03-215 1538', N'Images/Morning Burger茉莉漢堡banner.jpp.jpg', N'Images/Morning Burger茉莉漢堡.jpeg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (14, N'教父牛排 Danny''s Steakhouse', N'西式', N'台北市中山區樂群三路58號', N'教父牛排 Danny''s Steak House 品牌故事

以教父級品質保證之意而命名的「教父牛排」

是鄧有癸可以真正從理念到餐點皆能符合自己要求的牛排館

這個夢想在多年來為各五星級飯店操刀開設出一家家成功的專業牛排館之後 他終於實現

如今隨著冷藏牛肉進口品質提升以及熟成技術的進步，加上國人對於牛排喜好的需求提升，

怎樣能做出一塊更好的牛排則是我一直在努力追求的」。', N'02-85011838', N'Images/教父牛排 Dbanner.jpg', N'Images/教父牛排PHOTO.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (15, N'豪哥海鮮熱炒', N'中式', N'桃園市中埔六街81號', N'店內環境

簡簡淨淨、明亮明亮

還有電視、冷氣、電風扇

很適合家庭聚餐
價格來說，相當的平價

整餐吃下來我們二個人才花320元

而且味道也不差

有機會可以來吃吃看~~!!
營業時間：週一-週日:11:00 - 21:30', N'0925151692', N'Images/豪哥快炒BANNER.jpg', N'Images/1605013642-1876735125-g_l.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (16, N'小東門蒸烤鮮飯食', N'中式', N'新竹市東區大同路86號 (東門市場1082室)', N'人氣名店"小東門-蒸烤鮮飯食"。
結合了日本、台灣及粵式料理，是學生、上班族消夜聚餐的最佳選擇！
營業時間： 17:00~23:00', N'03-5237883', N'Images/小東門。蒸烤鮮飯食banner.jpg', N'Images/小東門蒸烤鮮飯食.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (17, N'靜岡勝政豬排', N'日式', N'桃園市中壢區中園路二段501號GBF', N'於內甜美細緻又多汁」的完美之境！是靜岡勝政豬排的使命！ 位於富士山腳下的靜岡勝政日式豬排，為日本靜岡縣屹立近20年的指標性排隊豬排名店，是當地人氣不墜的豬排名店。為呈現最原汁原味的靜岡勝政日式豬排風味饗宴，從食材、製作、食器選擇、服務，無一不細心嚴選打造，以期完整體現靜岡勝政日式豬排不斷追求「究極的日式豬排」的精神。', N'034680000', N'Images/靜岡勝政豬排23.jpg', N'Images/靜岡勝政豬排20181228_181827.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (18, N'拉麵吧RamanBar', N'日式', N'桃園市中壢區大同路84號', N'說起日本美食就讓人想起串燒和拉麵，拉麵吧結合日本的美食代表，推出雞肉串燒拉麵，提供多種湯頭讓饕客依偏好選擇，讓來拉麵吧客人們花少少錢就可以品嚐到日本的代表美食。', N'03-4220111', N'Images/拉麵吧banner.jpg', N'Images/拉麵吧3.JPG')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (19, N'硬派主廚的軟嫩料理', N'燒肉', N'新竹市東區中央路102巷10號300(東門市場 1143號', N'選用USDA Choice美國特選級牛肉，採用低溫熟成的舒服料理技法製作，保留了每塊肉品的原汁原味，再將表皮炙燒到微焦，一口咬下軟嫩的口感，搭配季節時蔬的享用，拌入半熟溫泉蛋、古早味肉燥飯，整體料理不過度烹調反而保留了食材的真實味道，肉肉控的必點料理～', N'03-5251323', N'Images/硬派主廚banner.JPG', N'Images/硬派主廚.jpg')
GO
INSERT [dbo].[Store] ([id], [stname], [sclass], [saddress], [stitd], [tel], [bannerurl], [photourl]) VALUES (20, N'RAINBOWPAPA', N'下午茶', N'桃園市中壢區幸福新村3號', N'每週都供應不同的主廚特餐，讓饕客每次來都有不一樣的味覺感受與驚喜，走進RAINBOW PAPA，感受全新的幸福滋味。', N'03-4655377', N'Images/RAINBOWPAPAbanner.JPG', N'Images/RAINBOWPAPAphoto.JPG')
GO
SET IDENTITY_INSERT [dbo].[Store] OFF
GO
