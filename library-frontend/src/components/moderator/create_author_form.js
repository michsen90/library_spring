import React, { useState } from "react";
import { Button, Col, Form, Row } from "react-bootstrap";
import { API } from "../../api_service";

function CreateAuthorForm({handleCloseFormNewAuthor}) {
    const [validated, setValidated] = useState(false);
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');

    const handleSubmit = evt => {
        evt.preventDefault();
        const form = evt.currentTarget;
        if (form.checkValidity() === false) {
            //todo
        }
        setValidated(true);
        API.createAuthor({firstname: firstname, lastname: lastname})
            .then(response => console.log(response))
            .catch(err => console.log(err));
            handleCloseFormNewAuthor();
    }

    return (
        <React.Fragment>
            <Form noValidate validated={validated} onSubmit={handleSubmit} style={{textAlign: "center"}}>
                <Row className="mb-3">
                    <Form.Group as={Col} md="6" controlId="validationFirstname">
                        <Form.Label>Firstname</Form.Label>
                        <Form.Control required type="text" placeholder="Firstname" value={firstname} onChange={evt => setFirstname(evt.target.value)} />
                    </Form.Group>
                    <Form.Group as={Col} md="6" controlId="validationLastname">
                        <Form.Label>Lastname</Form.Label>
                        <Form.Control required type="text" placeholder="Lastname" value={lastname} onChange={evt => setLastname(evt.target.value)} />
                    </Form.Group>
                </Row>
                <Button type="submit">Create author</Button>
            </Form>
        </React.Fragment>
    );
}

export default CreateAuthorForm;