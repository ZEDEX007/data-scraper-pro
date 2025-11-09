# Data Scraper Pro - Usage Guide

## Overview
Data Scraper Pro is a powerful tool for extracting data from websites using CSS Selectors and XPath. It supports multiple URLs and can export data in JSON, CSV, or HTML formats.

## Features

✅ **Multiple Extraction Methods**
- CSS Selectors for quick targeting
- XPath support for complex queries
- Regular expression support

✅ **Multi-URL Support**
- Scrape single or multiple URLs
- Batch processing
- Parallel execution

✅ **Flexible Export Formats**
- JSON (structured data)
- CSV (Excel-compatible)
- HTML (formatted tables)
- Database storage (SQLite, PostgreSQL)

✅ **Advanced Features**
- Dynamic page handling (JavaScript rendering)
- Automatic retry on failure
- Rate limiting to avoid blocking
- Proxy support
- User-Agent rotation

## Quick Start

### 1. Open the Tool
Open `index.html` in your web browser

### 2. Enter URL
Provide one or multiple URLs:
```
https://example.com/products
https://example.com/products?page=2
```

### 3. Define Fields
Add fields with CSS Selectors:

**Example 1: Product Scraping**
- Field Name: `Product Title` → Selector: `.product-name`
- Field Name: `Price` → Selector: `.product-price`
- Field Name: `Description` → Selector: `.product-desc`

**Example 2: News Scraping**
- Field Name: `Article Title` → Selector: `h2.article-title`
- Field Name: `Author` → Selector: `.author-name`
- Field Name: `Date` → Selector: `span.publish-date`

### 4. Select Export Format
- JSON (recommended for APIs)
- CSV (for Excel/Google Sheets)
- HTML (for reports)

### 5. Start Extraction
Click "Start Extraction" and download results

## CSS Selector Examples

```css
/* Class selector */
.product-title

/* ID selector */
#main-content

/* Attribute selector */
[data-price]
img[alt="Product"]

/* Descendant selector */
.container .item
.card h2.title

/* First child */
.list li:first-child

/* Multiple classes */
.btn.primary.large
```

## XPath Examples

```xpath
/* Basic path */
//div[@class='product']

/* Text matching */
//a[contains(text(), 'Buy')]

/* Attribute matching */
//*[@data-id='123']

/* Position */
//tr[1]/td[2]

/* Logical operators */
//div[@class='item' and @data-active='true']
```

## Best Practices

1. **Inspect Elements First**
   - Use browser DevTools (F12) to find correct selectors
   - Test selectors in Console

2. **Start Simple**
   - Extract one field at a time
   - Test with smaller datasets first

3. **Handle Errors**
   - Wrap selectors in try-catch
   - Provide default values

4. **Respect Robots.txt**
   - Check site's scraping policy
   - Add delays between requests
   - Use User-Agent headers

5. **Performance**
   - Batch similar URLs
   - Limit concurrent connections
   - Cache results when possible

## Troubleshooting

**Issue: "No results found"**
- Check if selector is correct
- Verify page structure hasn't changed
- Check browser console for errors

**Issue: Timeout errors**
- Increase timeout value
- Check internet connection
- Try with smaller dataset

**Issue: Empty fields**
- Selector might not match
- Content might be loaded dynamically
- Try XPath alternative

## API Documentation

### Backend Endpoints (Coming Soon)

```bash
POST /api/scrape
GET /api/results/{id}
DELETE /api/results/{id}
```

## Contributing

We welcome contributions! Please:
1. Fork the repository
2. Create feature branch
3. Submit pull request

## License

MIT License - See LICENSE file

## Support

For issues and questions:
- GitHub Issues: https://github.com/ZEDEX007/data-scraper-pro/issues
- Discussions: https://github.com/ZEDEX007/data-scraper-pro/discussions
