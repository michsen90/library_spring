import React, { useEffect, useState } from 'react';
import Logo from './assets/logo.png'
import './App.css';
import { Col, Nav, Navbar, Row } from 'react-bootstrap';
import About from './components/about';
import BooksList from './components/books_list';
import LoginForm from './components/login_form';
import ModeratorLayout from './components/moderator/moderator';

function App() {
  const [authorized, setAuthorized] = useState(false);
  const [role, setRole] = useState('');
  const [page, setPage] = useState('main');

  useEffect(() => {
    const user = sessionStorage.getItem("Authorization");
    if (user !== null) {
      setAuthorized(true);
      setRole(sessionStorage.getItem('role'));
    } else {
      setAuthorized(false);
    }

  }, [setAuthorized])

  const logout = () => {
    sessionStorage.removeItem("userId");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("Authorization");
    window.location.reload();
  }

  const swichPage = () => {
    switch (page) {
      case 'books':
        return <BooksList />;
      case 'manage-moderator':
        return <ModeratorLayout />
      default:
        return <About />;
    }
  }

  return (
    <div className="App">
      {authorized ?
        <div>
          <Navbar bg='dark' variant='dark'>
            <Navbar.Brand onClick={() => setPage('main')} style={{ cursor: "pointer" }}>
              <img src={Logo} width="70" height="60" alt='Library' />
            </Navbar.Brand>
            <Navbar.Brand onClick={() => setPage('main')} style={{ cursor: "pointer" }}>
              <Navbar.Text>Library System</Navbar.Text>
            </Navbar.Brand>
            <Nav className="mr-auto">
              <Nav.Link onClick={() => setPage('books')}>Search Books</Nav.Link>
            </Nav>
            <Navbar.Collapse className="justify-content-end">
              {role === 'MODERATOR' ?
                <Nav>
                  <Nav.Link onClick={() => setPage('manage-moderator')}>Manage - moderator mode</Nav.Link>
                </Nav>
                : role === 'ADMIN' ?
                  <Nav.Link onClick={() => setPage('manage-admin')}>Manage - Admin mode</Nav.Link>
                  : null}
              {authorized ?
                <Nav style={{ marginRight: "20px" }}>
                  <Nav.Link onClick={logout}>Logout</Nav.Link>
                </Nav>
                :
                <Nav >
                  <Nav.Link >Login</Nav.Link>
                </Nav>
              }
            </Navbar.Collapse>
          </Navbar>
          <Row>
            <Col>
              {swichPage()}
            </Col>
          </Row>
        </div>
        :
        <LoginForm />}

    </div>
  );
}

export default App;
