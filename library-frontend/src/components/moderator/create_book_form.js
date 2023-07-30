import React, { useEffect, useState } from "react";
import { API } from "../../api_service";
import { Button, Col, Form, Row } from "react-bootstrap";

function CreateBookForm({ handleCloseFormNewBook, setCreatedBook }) {

    const [validated, setValidated] = useState(false);
    const [title, setTitle] = useState('');
    const [ISBN, setISBN] = useState('');
    const [selectedAuthor, setSelectedAuthor] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('');

    const [authors, setAuthors] = useState([]);
    const [categories, setCategories] = useState([]);
    

    useEffect(() => {
        API.getAllAuthors().then(response => setAuthors(response.data)).catch(err => console.log(err));
    }, []);

    useEffect(() => {
        API.getAllCategories().then(response => setCategories(response.data)).catch(err => console.log(err));
    }, []);

    const handleAuthor = evt => {
        setSelectedAuthor(JSON.parse(evt.target.value));
    }

    const handleSubmit = evt => {
        evt.preventDefault();
        const form = evt.currentTarget;
        if (form.checkValidity() === false) {
            //todo
        }
        const authorsToSave = [];
        const categoriesToSave = [];
        authorsToSave.push(selectedAuthor);
        categoriesToSave.push(selectedCategory);
        setValidated(true);
        API.createBook({title: title, isbn: ISBN, authors: authorsToSave, categories: categoriesToSave})
            .then(response => setCreatedBook(response.data))
            .catch(err => console.log(err));
        
        handleCloseFormNewBook();
    }

    return (
        <React.Fragment>
            <Form noValidate validated={validated} onSubmit={handleSubmit} style={{textAlign: "center"}}>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control required type="text" placeholder="title" value={title} onChange={evt => setTitle(evt.target.value)} />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group as={Col} controlId="validationISBN">
                        <Form.Label>ISBN</Form.Label>
                        <Form.Control required type="text" placeholder="ISBN" value={ISBN} onChange={evt => setISBN(evt.target.value)} />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Select aria-label="Authors" onChange={handleAuthor}>
                        <option>Select author</option>
                        {authors && authors.map(author => {
                            return (
                                <option key={author.id} value={JSON.stringify(author)}>{author.firstname} {author.lastname}</option>
                            )
                        })}
                    </Form.Select>
                </Row>
                <Row className="mb-3">
                    <Form.Select aria-label="Categories" onChange={evt => setSelectedCategory(JSON.parse(evt.target.value))} value={selectedCategory} >
                        <option >Select category</option>
                        {categories && categories.map(category => {
                            return (
                                <option key={category.id} value={JSON.stringify(category)} >{category.bookType}</option>
                            )
                        })}
                    </Form.Select>
                </Row>
                <Button type="submit">Create book</Button>
            </Form>
        </React.Fragment>
    );
}

export default CreateBookForm;