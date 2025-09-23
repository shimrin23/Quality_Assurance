const express = require('express');
const path = require('path');
const { createProxyMiddleware } = require('http-proxy-middleware');
const app = express();

app.use(express.static(path.join(__dirname, 'build')));

// All API requests will be forwarded to the backend server
app.use('/api', createProxyMiddleware({
  target: 'http://localhost:8080', // Your Spring Boot backend server
  changeOrigin: true, // Needed for virtual hosted sites
}));

app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname, 'build', 'index.html'));
});

app.listen(3000, () => {
	console.log('Listening on port 3000');
	console.log('API requests will be proxied to http://localhost:8080');
});