CREATE TABLE [invoice](
	[id] [int] NOT NULL,
	[buyer] [varchar](max) NULL,
	[image_name] [varchar](max) NULL,
	[invoice_amount] [varchar](max) NULL,
	[invoice_currency] [varchar](max) NULL,
	[invoice_due_date] [varchar](max) NULL,
	[invoice_image] [varchar](max) NULL,
	[invoice_number] [varchar](max) NULL,
	[invoice_status] [varchar](max) NULL,
	[supplier] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


