const MessageReceived = (props) => {
    return (
        <div>
            {props.text}
        </div>
    );
};

const ChatMessagesPlaceholder = (props) => {
    console.log(props);
    return (
        <>
            <h2>Messages:</h2>
            {props.messagesReceived.map((message, i) => {
                return <MessageReceived key={i} text={message.text} />
            })}
        </>
    );
}

export default ChatMessagesPlaceholder;