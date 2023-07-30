import React, { useState } from "react";
import { Alert, Button, Col, Form, Row } from "react-bootstrap";
import { API } from "../api_service";

function LoginForm() {

    const [validated, setValidated] = useState(false);
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [alertLogin, setAlertLogin] = useState(false);
    const [alertPassword, setAlertPassword] = useState(false);

    const handleLogin = evt => {
        evt.preventDefault();
        const form = evt.currentTarget;
        if (form.checkValidity() === false) {
            if (login === '') {
                setAlertLogin(true);
            } else {
                setAlertLogin(false);
            }
            if (password === '') {
                setAlertPassword(true);
            } else {
                setAlertPassword(false);
            }
            setValidated(false);
        } else {
            setValidated(true);
            API.login({ username: login, password: password })
                .then(response => {
                    sessionStorage.setItem("userId", response.data.id);
                    sessionStorage.setItem("role", response.data.roles[0]);
                    sessionStorage.setItem("Authorization", response.data.token);
                    window.location.reload();
                })
                .catch(err => console.log(err));

        }
    }

    return (
        <React.Fragment>
            <div className="login">
                <Row>
                    <Col sm={4}></Col>
                    <Col >
                        <h2 style={{ marginTop: "10%" }}>Library system</h2>
                        <h4>Please login to the system!</h4>
                        <Form noValidate validated={validated} style={{ textAlign: "center", marginTop: "5px" }} onSubmit={handleLogin}>
                            <Row>
                                <Form.Group as={Col} controlId="validationLogin">
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control required type="text" placeholder="Enter your username" value={login} onChange={evt => setLogin(evt.target.value)} />
                                    <Form.Control.Feedback type="invalid">Enter valid login!</Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            {alertLogin ?
                                <Alert variant="danger" style={{ marginTop: "5px" }}>
                                    Enter valid username!
                                </Alert>
                                : null}
                            <Row>
                                <Form.Group as={Col} controlId="validationPassword">
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control required type="password" placeholder="Enter you password" value={password} onChange={evt => setPassword(evt.target.value)} />
                                    <Form.Control.Feedback type="invalid">Enter your password!</Form.Control.Feedback>
                                </Form.Group>
                            </Row>
                            {alertPassword ?
                                <Alert variant="danger" style={{ marginTop: "5px" }}>
                                    Enter valid password!
                                </Alert>
                                : null}
                            <Button style={{ marginTop: "10px", width: "30%" }} type="submit" variant="success">Login!</Button>
                        </Form>
                    </Col>
                    <Col sm={4}></Col>
                </Row>
            </div>
        </React.Fragment>
    );
}

export default LoginForm;