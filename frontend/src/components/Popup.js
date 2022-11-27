import React, {useState} from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import SoftTypography from "./SoftTypography";
import SoftButton from "./SoftButton";
import SoftBox from "./SoftBox";
import {Grid} from "@mui/material";

function Popup(props) {
    const params = props.props;
    const [show, setShow] = useState(false);
    const axiosPrivate = useAxiosPrivate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const handleSubmit = async () => {
        console.log(params);
        try {
            const data = {
                origin: params.Origin,
                destination: params.Destination,
                departureDate: params.Departure,
                returnDate: params.Return,
                travelClass: params.travelClass,
                flightType: params.flightType,
                adults: params.Adults,
                children: params.Children,
                infants: params.Infants,
            }
            const response = await axiosPrivate.post(`/alerts`, (data));
            if (isNaN(response.data)) {
                console.log("Success");
                handleClose();
                return;
            }
            console.log("Error");
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <>
            <Button variant="outline-dark" onClick={handleShow}>
                Set price alert
            </Button>

            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Grid container justifyContent="center" sx={{textAlign: "center"}}>
                        <Modal.Title>
                            <SoftTypography variant="h3" color="dark" fontWeight="bold">
                                Create a new Price Alert
                            </SoftTypography>
                        </Modal.Title>
                    </Grid>
                </Modal.Header>
                <Modal.Body>
                    <Grid container justifyContent="center" sx={{textAlign: "center", flexDirection: "column"}}>
                        <SoftTypography varient="h5" fontWeight="bold" fontSize={18}>
                            Ticket prices change all the time. We can’t prevent it, but we can let you know.
                        </SoftTypography>
                        <br/>
                        <SoftBox>
                            <SoftTypography varient="h6" fontWeight="medium" fontSize={14}>
                                Trip details:
                            </SoftTypography>
                        </SoftBox>
                        <SoftBox>
                            <SoftTypography varient="h6" fontSize={14}>Fly
                                from {params.Origin} to {params.Destination}
                            </SoftTypography>
                        </SoftBox>
                        <SoftBox>
                            <SoftTypography varient="h6"
                                            fontSize={14}>From {params.Departure} to {params.Return}
                            </SoftTypography>
                        </SoftBox>
                    </Grid>
                </Modal.Body>
                <Modal.Footer>
                    <SoftBox mr={1}>
                        <SoftButton color="dark" variant="outlined" onClick={handleClose}>
                            Close
                        </SoftButton>
                    </SoftBox>
                    <SoftBox ml={1}>
                        <SoftButton color="dark" onClick={handleSubmit}>
                            Notify Me
                        </SoftButton>
                    </SoftBox>
                </Modal.Footer>
            </Modal>
        </>
    );
}

export default Popup;