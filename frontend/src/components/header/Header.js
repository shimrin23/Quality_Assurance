import React from 'react';
import { Link } from 'react-router-dom';

function Header({isAuthenticated, setIsAuthenticated}) {
	return (
		<header>
			<nav className="navbar navbar-expand-lg navbar-dark sticky-top" style={{background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'}}>
				<div className="container">
					<Link className="navbar-brand fw-bold fs-3" to="/" style={{color: '#fff', textDecoration: 'none'}}>
						 ToDoList App
					</Link>
					
					<button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
						<span className="navbar-toggler-icon"></span>
					</button>
					
					<div className="collapse navbar-collapse" id="navbarNav">
						<ul className="navbar-nav ms-auto">
							<li className="nav-item">
								<Link className="nav-link px-3" to="/" style={{color: '#fff', fontWeight: '500'}}> Home</Link>
							</li>
							{isAuthenticated && (
								<li className="nav-item">
									<Link className="nav-link px-3" to="/todo" style={{color: '#fff', fontWeight: '500'}}>📋 View Todo</Link>
								</li>
							)}
							{isAuthenticated && (
								<li className="nav-item">
									<Link className="nav-link px-3" to="/add" style={{color: '#fff', fontWeight: '500'}}>➕ Add Todo</Link>
								</li>
							)}
							{!isAuthenticated && (
								<li className="nav-item">
									<Link className="nav-link px-3" to="/signin" style={{color: '#fff', fontWeight: '500'}}>🔐 Sign In</Link>
								</li>
							)}
							{!isAuthenticated && (
								<li className="nav-item">
									<Link className="nav-link px-3" to="/signup" style={{color: '#fff', fontWeight: '500'}}>📝 Sign Up</Link>
								</li>
							)}
							{isAuthenticated && (
								<li className="nav-item">
									<Link className="nav-link px-3" to="/signout" style={{color: '#fff', fontWeight: '500'}}>🚪 Sign Out</Link>
								</li>
							)}
							<li className="nav-item">
								<Link className="nav-link px-3" to="/about" style={{color: '#fff', fontWeight: '500'}}>ℹ️ About</Link>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
	)
}

export default Header;