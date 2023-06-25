'use strict';

// var usernamePage = document.querySelector('#username-page');
// var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var gpMembers=document.querySelector("#gpMembers");
var groupInfo=document.querySelector("#group-info");
var gptitle=document.querySelector("#gptitle");
var gpDescp=document.querySelector("#gpDescp");
var gpAbbvn=document.querySelector("#gpAbbvn");


// Select the buttons
const connectButton = document.querySelector('#connect');
const disconnectButton = document.querySelector('#disconnect');

connectButton.disabled = false;
disconnectButton.disabled = true;
disconnectButton.style.backgroundColor='rgba(244, 67, 54, 0.5)';


groupInfo.style.display="none";
// var messageArea = document.querySelector('#messageArea');
// var connectingElement = document.querySelector('.connecting');
//
var connectClick=document.querySelector("#connect");
var stompClient = null;
var username = null;
var userTopic =null;
//
// var colors = [
//     '#2196F3', '#32c787', '#00BCD4', '#ff5652',
//     '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
// ];

function connect(event) {
    event.preventDefault();
    username = document.querySelector('#name').value.trim();
    userTopic=document.querySelector('#userTopic').value.trim();
    if(username) {
    //     usernamePage.classList.add('hidden');
    //     chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }

}
function  disconnect(){
    var confirmation=confirm("Do you want to leave group");
    if(confirmation){
        stompClient.unsubscribe(userTopic);
        connectButton.disabled = false;
        disconnectButton.disabled = true;
        connectButton.style.backgroundColor='#008CBA';
        disconnectButton.style.backgroundColor='rgba(244, 67, 54, 0.5)';

    }
}


function onConnected() {
    // Subscribe to the Public Topic

    // Tell your username to the server
    if(userTopic==='public'){
        stompClient.subscribe('/topic/'+userTopic, onMessageReceived);
        stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify({sender: username, type: 'JOIN'})
        )
        // connectingElement.classList.add('hidden');
        gptitle.innerHTML="Distributed Sytems";
        gpDescp.innerHTML="Break down any complex ideas in distributed systems ";
        gpAbbvn.innerHTML="D";
    }
    if(userTopic==='football'){
        stompClient.subscribe('/group/'+userTopic, onMessageReceived);
        stompClient.send("/app/chat.addFootball",
            {},
            JSON.stringify({sender: username, type: 'JOIN'})
        )

        // connectingElement.classList.add('hidden');
        gptitle.innerHTML="Computer Networks";
        gpDescp.innerHTML="Full understanding of computer networks ";
        gpAbbvn.innerHTML="C"
        // connectingElement.classList.add('hidden');
    }

}


function onError(error) {
    console.log("error while connecting")
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageContent,
            type: 'CHAT'
        };
        if(userTopic==='public'){
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        // if(userTopic==='football'){
        //     stompClient.send("/app/chat.sendFootballMessage", {}, JSON.stringify(chatMessage));
        //     messageInput.value = '';
        // }
    }
    event.preventDefault();
}
//
//
function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);
    console.log("message received from "+message.sender);

//     var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {

        // Disable button1 and enable button2
        connectButton.disabled = true;
        disconnectButton.disabled = false;
        connectButton.style.backgroundColor='rgba(0, 140, 186, 0.2)';
        disconnectButton.style.backgroundColor='#f44336';
        // messageElement.classList.add('event-message');
        // message.content = message.sender + ' joined!';
        console.log("new participant joined");
        // Create a new <li> element
        const newListItem = document.createElement('li');

        if(username===message.sender) {
            newListItem.textContent = 'You have joined the group';
            groupInfo.style.display="block";

        }
        else {
            newListItem.textContent = message.sender + ':has joined the group';

        }
        newListItem.className="joined-members";

        // Append the new <li> element to the <ul> element
        gpMembers.appendChild(newListItem);

    }
    else if (message.type === 'LEAVE') {
        // messageElement.classList.add('event-message');
        // message.content = message.sender + ' left!';
        console.log("participant left");
        // Create a new <li> element
        const newListItem = document.createElement('li');

        if(username===message.sender) {
            newListItem.textContent = 'You have left the group';

        }
        else {
            newListItem.textContent = message.sender + ':has left the group';

        }
        newListItem.className="left-members";

        // Append the new <li> element to the <ul> element
        gpMembers.appendChild(newListItem);
    } else {

        // Create a new <div> element with class "msg"
        var newDiv = document.createElement('div');
        newDiv.className = 'msg';

        // Create <p> elements for the inner content
        var youParagraph = document.createElement('p');

        if(username===message.sender) {
            newDiv.classList.add("messages_mine");
            youParagraph.style.color = 'blue';
            youParagraph.textContent = 'You';
        }
        else {
            newDiv.classList.add('messages_frnd')
            youParagraph.style.color = 'deeppink';
            youParagraph.textContent = message.sender;
        }


        var messageParagraph = document.createElement('p');
        messageParagraph.textContent = message.content;

// Append the <p> elements to the new <div> element
        newDiv.appendChild(youParagraph);
        newDiv.appendChild(messageParagraph);

// Get the outermost <div> element with class "chat-content"
        var conversationDiv = document.getElementById('conversation');

// Append the new <div> element to the outermost <div>
        conversationDiv.appendChild(newDiv);
        conversationDiv.scrollTop = conversationDiv.scrollHeight;
    }

}


usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
disconnectButton.addEventListener('click', disconnect, true);