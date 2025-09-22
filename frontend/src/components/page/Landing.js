import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

export default function Landing({isAuthenticated, setIsAuthenticated}) {
  const [message, setMessage] = useState('')
  const [numberAllTodoNotCompleted, setNumberAllTodoNotCompleted] = useState(0);
  const [numberAllTodo, setNumberAllTodo] = useState(0);
  const [errorMessage, setErrorMessage] = useState('');

  const showErrorMessage = () => {
    if(errorMessage === ''){
      return <div></div>
    }

    return <div className="alert alert-danger alert-dismissible fade show" role="alert" style={{borderRadius: '15px', border: 'none', boxShadow: '0 4px 6px rgba(0,0,0,0.1)'}}>
      <strong>⚠️ Error:</strong> {errorMessage}
      <button type="button" className="btn-close" data-bs-dismiss="alert"></button>
    </div>
  }

  useEffect(() => {
    async function getAndSetNumberAllTodo() {
      try{
        const response = await axios.get('http://localhost:3001/api/todo/count', {
          headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`,
          }
        });
        setNumberAllTodo(response.data.count);
      } catch (error) {
        setMessage('');
        if (error.response) {
          setErrorMessage(error.response.data.message);
        } else {
          setErrorMessage('Error: something happened');
        }
      }
    }

    async function getAndSetNumberAllTodoNotCompleted() {
      try{
        const response = await axios.get('http://localhost:3001/api/todo/count?isCompleted=false', {
          headers: {
            'Authorization': `Bearer ${sessionStorage.getItem('token')}`,
          }
        });

        setNumberAllTodoNotCompleted(response.data.count);
      } catch (error) {
        setMessage('');
        if (error.response) {
          setErrorMessage(error.response.data.message);
        } else {
          setErrorMessage('Error: something happened');
        }
      }
      
    }
    if(isAuthenticated){
      getAndSetNumberAllTodo();
      getAndSetNumberAllTodoNotCompleted();
      setMessage(`Welcome, ${sessionStorage.getItem('name')}. You have ${numberAllTodoNotCompleted} todo not completed out of ${numberAllTodo} todo.`);
    } else {
      setMessage('Please sign in to continue');
    }
  }, [isAuthenticated, numberAllTodo, numberAllTodoNotCompleted])

	return (
		<div className="container-fluid" style={{minHeight: '80vh', background: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)'}}>
			<div className="container py-5">
				<div className="row justify-content-center">
					<div className="col-lg-8 col-md-10 col-12">
						<div className="text-center mb-5">
							<h1 className="display-4 fw-bold text-primary mb-4" style={{color: '#667eea !important'}}>
								🚀 Todo List Application
							</h1>
							<p className="lead text-muted">
								Stay organized and boost your productivity with our intuitive todo management system
							</p>
						</div>
						
						{showErrorMessage()}
						
						{isAuthenticated ? (
							<div className="row justify-content-center">
								<div className="col-md-6">
									<div className="card border-0 shadow-lg" style={{borderRadius: '20px', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'}}>
										<div className="card-body text-center text-white p-5">
											<h3 className="mb-4">👋 Welcome back, {sessionStorage.getItem('name')}!</h3>
											<div className="row g-3 mb-4">
												<div className="col-6">
													<div className="bg-white bg-opacity-20 rounded-3 p-3" style={{minHeight: '80px', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
														<h4 className="mb-1 fw-bold" style={{fontSize: '2rem', color: '#000000'}}>{numberAllTodoNotCompleted || 0}</h4>
														<p className="mb-0 small fw-bold" style={{color: '#000000'}}>Pending Tasks</p>
													</div>
												</div>
												<div className="col-6">
													<div className="bg-white bg-opacity-20 rounded-3 p-3" style={{minHeight: '80px', display: 'flex', flexDirection: 'column', justifyContent: 'center'}}>
														<h4 className="mb-1 fw-bold" style={{fontSize: '2rem', color: '#000000'}}>{numberAllTodo || 0}</h4>
														<p className="mb-0 small fw-bold" style={{color: '#000000'}}>Total Tasks</p>
													</div>
												</div>
											</div>
											<div className="mt-4">
												<Link to="/todo" className="btn btn-light btn-lg px-4 me-3 mb-2">📋 View Todos</Link>
												<Link to="/add" className="btn btn-outline-light btn-lg px-4">➕ Add New</Link>
											</div>
										</div>
									</div>
								</div>
							</div>
						) : (
							<div className="row justify-content-center">
								<div className="col-md-6">
									<div className="card border-0 shadow-lg" style={{borderRadius: '20px'}}>
										<div className="card-body text-center p-5">
											<h3 className="text-primary mb-4">🔐 Get Started</h3>
											<p className="text-muted mb-4">Sign in to access your personalized todo list and start organizing your tasks</p>
											<div>
												<Link to="/signin" className="btn btn-primary btn-lg px-4 me-3">Sign In</Link>
												<Link to="/signup" className="btn btn-outline-primary btn-lg px-4">Sign Up</Link>
											</div>
										</div>
									</div>
								</div>
							</div>
						)}
					</div>
				</div>
			</div>
		</div>
	)
}