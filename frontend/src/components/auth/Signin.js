import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useHistory } from "react-router-dom"; 

function Signin({isAuthenticated, setIsAuthenticated}) {
	const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  let history = useHistory();

  function timeout(delay) {
    return new Promise( res => setTimeout(res, delay) );
  }

	const onSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/signin', {username, password});
      sessionStorage.setItem('token', response.data.token);
      sessionStorage.setItem('name', response.data.username);
      setIsAuthenticated(true);
    } catch(error){
      setMessage('');
      if (error.response) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage('Error: something happened');
      }
      setIsAuthenticated(false);
      return;
    }
    
    setUsername('');
    setPassword('');
    setErrorMessage('');
    setMessage('Sign in successful');
    await timeout(1000);
    history.push("/");
  }

  useEffect(() => {
    setMessage('')
  }, [username, password])

  const showMessage = () => {
    if(message === ''){
      return <div></div>
    }
    return <div className="alert alert-success alert-dismissible fade show" role="alert" style={{borderRadius: '15px', border: 'none', boxShadow: '0 4px 6px rgba(0,0,0,0.1)'}}>
      <strong>🎉 Success!</strong> {message}
      <button type="button" className="btn-close" data-bs-dismiss="alert"></button>
    </div> 
  }

  const showErrorMessage = () => {
    if(errorMessage === ''){
      return <div></div>
    }

    return <div className="alert alert-danger alert-dismissible fade show" role="alert" style={{borderRadius: '15px', border: 'none', boxShadow: '0 4px 6px rgba(0,0,0,0.1)'}}>
      <strong>⚠️ Error:</strong> {errorMessage}
      <button type="button" className="btn-close" data-bs-dismiss="alert"></button>
    </div>
  }

	return (
		<div className="container py-5" style={{minHeight: '80vh', background: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)'}}>
			<div className="row justify-content-center">
				<div className="col-lg-5 col-md-7 col-12">
					<div className="card border-0 shadow-lg" style={{borderRadius: '20px'}}>
						<div className="card-body p-5">
							<div className="text-center mb-4">
								<h1 className="text-primary fw-bold" style={{color: '#667eea !important'}}>🔐 Sign In</h1>
								<p className="text-muted">Welcome back! Please sign in to your account</p>
							</div>
							
							<form onSubmit={onSubmit}>
								<div className="mb-4">
									<label className="form-label fw-bold">👤 Username</label>
									<input 
										value={username} 
										onChange={e => setUsername(e.target.value)} 
										placeholder="Enter your username"
										className="form-control form-control-lg"
										style={{borderRadius: '15px', border: '2px solid #e9ecef'}}
										required
									/>
								</div>
								
								<div className="mb-4">
									<label className="form-label fw-bold">🔒 Password</label>
									<input 
										value={password} 
										type="password" 
										onChange={e => setPassword(e.target.value)}
										placeholder="Enter your password"
										className="form-control form-control-lg"
										style={{borderRadius: '15px', border: '2px solid #e9ecef'}}
										required
									/>
								</div>
								
								<div className="d-grid">
									<button className="btn btn-primary btn-lg" style={{borderRadius: '15px', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', border: 'none'}}>
										🚀 Sign In
									</button>
								</div>
							</form>
							
							{showMessage()}
							{showErrorMessage()}
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}

export default Signin;