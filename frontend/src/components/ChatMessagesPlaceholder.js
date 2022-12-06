import SoftAlert from "./SoftAlert";
import {Grid} from "@mui/material";

const MessageReceived = (props) => {
    return (
            <div>
                <Grid
                    container
                    direction="row"
                    justifyContent="flex-end"
                    alignItems="flex-end"
                >
                    <SoftAlert dismissible={true}>{props.text}</SoftAlert>
                </Grid>
{/*                {props.text}*/}
            </div>
    );
};

const ChatMessagesPlaceholder = (props) => {
    console.log(props);
    return (
        <>
{/*
            <h2>Messages:</h2>props
*/}
            {props.messagesReceived.map((message, i) => {
                return <MessageReceived key={i} text={message.text}/>
                
            })}
        </>
    );
}

export default ChatMessagesPlaceholder;