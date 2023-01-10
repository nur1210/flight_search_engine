import React, { useState } from 'react';
import {Box, Button, Modal, TextField} from "@mui/material";
import MessageIcon from '@mui/icons-material/Message';
import SoftButton from "./SoftButton";
import SoftInput from "./SoftInput";
import SoftBox from "./SoftBox";
import useStompClient from "../hooks/useStompClient";

const TextInputModal = ({email}) => {
    const [open, setOpen] = useState(false);
    const [text, setText] = useState('');
    const {client} = useStompClient();

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        boxShadow: 24,
        pt: 2,
        px: 2,
        pb: 2,
    };
    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleTextChange = (event) => {
        setText(event.target.value);
    };

    const handleSendClick = () => {
        console.log(`Sending: ${text}`);
        setText('');
        setOpen(false);
    };

    const sendMessage = () => {
            client.send('/app/specific-notification', {}, JSON.stringify({
                'text': text,
                'to': email,
                'title': 'Admin message',
                'date': (new Date()).toDateString()
            }));
            handleClose();
    };

    return (
        <>
            <SoftButton iconOnly onClick={handleOpen}>
                <MessageIcon />
            </SoftButton>
            <Modal open={open} onClose={handleClose}>
                <SoftBox  sx={{...style}}>
                    <SoftInput
                        type="text"
                        placeholder="Enter text"
                        value={text}
                        onChange={handleTextChange}
                        fullWidth
                    />
                    <SoftButton
                        variant="gradient"
                        color="dark"
                        onClick={handleSendClick}
                        disabled={text === ''}
                        sx={{ marginTop: '1rem' }}
                        onClick={sendMessage}
                    >
                        Send
                    </SoftButton>
                </SoftBox>
            </Modal>
        </>
    );
};

export default TextInputModal;