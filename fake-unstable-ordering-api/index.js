const express = require('express');
const { v4: uuidv4 } = require('uuid');

const app = express();
const PORT = 3000;

let online = true;

// Alterna o estado da API a cada 40 segundos (30 on / 10 off)
setInterval(() => {
    online = !online;
    console.log(`>> API is now ${online ? 'ONLINE' : 'OFFLINE'}`);
}, 40000); // 40s ciclo

app.get('/resale-order', (req, res) => {
    if (!online) {
        return res.status(503).json({ error: 'API temporarily unavailable' });
    }

    res.json({ uuid: uuidv4() });
});

app.listen(PORT, () => {
    console.log(`Running...  http://localhost:${PORT}`);
});
