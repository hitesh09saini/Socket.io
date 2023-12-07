require('dotenv').config();
const express = require('express')
const cors = require('cors')
const socketIO = require('socket.io')


const app = express();
app.use(cors());

const server = app.listen(process.env.PORT, ()=>{
    console.log(`server is running on http://localhost:${process.env.PORT}`);
})

const io = socketIO(server);

app.get('/', (req, res)=>{
    res.sendFile(__dirname+'/index.html')
})



io.on('connection',(socket)=>{
    console.log('A user Connected');

    socket.on('join', (username)=>{
        io.emit('chat message', `${username} has joined the chat`);
    })

     socket.on('chat message', (msg) => {
    // Broadcast the message to all clients
    io.emit('chat message', msg);
  });


    socket.on('disconnect', ()=>{
        console.log('User disconnected');
    })

    socket.on('message', (data)=>{
        if(!data.message){
            return
        }
        console.log(`Message from ${socket.id}: ${data.message}`);
        io.emit('message', `Server: ${data}`);
    })

})



