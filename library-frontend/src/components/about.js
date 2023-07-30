import React from "react";
import { Col } from "react-bootstrap";

function About() {

    return (
        <React.Fragment>
            <Col style={{ textAlign: "start", marginTop: "10px" }}>
                <div style={{marginLeft: "15%", marginRight: "15%"}}>
                    <h4>Library system</h4>
                    <p>This is the library system build on Spring and React.</p>
                    <h5>Options:</h5>
                    <h6>1. User:</h6>
                    <p>
                        - Searching books (by ISBN or title)
                        - Book reantal - todo.
                        - Account management.
                    </p>
                    <h6>2. Moderator:</h6>
                    <p>
                        - Managing books(adding new, deleting exists, updating).
                        - Managing authors(adding new, deleting exists, updating).
                    </p>
                    <h6>2. Admin:</h6>
                    <p>
                        - Managing books(adding new, deleting exists, updating).
                        - Managing authors(adding new, deleting exists, updating).
                        - Managing account users(adding new, deleting exists, updating, changing roles).
                    </p>
                </div>
            </Col>
        </React.Fragment>
    );
}

export default About;