import React from 'react';

export default function About() {
	return (
		<div className="container-fluid" style={{minHeight: '80vh', background: 'linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)'}}>
			<div className="container py-5">
				<div className="row justify-content-center">
					<div className="col-lg-8 col-md-10 col-12">
						<div className="text-center mb-5">
							<h1 className="display-4 fw-bold text-primary mb-4" style={{color: '#667eea !important'}}>
								ℹ️ About Our App
							</h1>
							<p className="lead text-muted">
								Learn more about our powerful todo management system
							</p>
						</div>
						
						<div className="row g-4">
							<div className="col-lg-6 col-md-6 col-12">
								<div className="card border-0 shadow-lg h-100" style={{borderRadius: '20px'}}>
									<div className="card-body text-center p-5">
										<div className="mb-4">
											<h2 className="text-primary mb-3" style={{color: '#667eea !important'}}>🚀 Features</h2>
										</div>
										<div className="text-start">
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Create and manage todos</span>
											</div>
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Set target dates</span>
											</div>
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Mark tasks as completed</span>
											</div>
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Update existing todos</span>
											</div>
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Secure user authentication</span>
											</div>
											<div className="d-flex align-items-center mb-3">
												<span className="me-3">✅</span>
												<span>Responsive design</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div className="col-lg-6 col-md-6 col-12">
								<div className="card border-0 shadow-lg h-100" style={{borderRadius: '20px'}}>
									<div className="card-body text-center p-5">
										<div className="mb-4">
											<h2 className="text-primary mb-3" style={{color: '#667eea !important'}}> App Information</h2>
										</div>
										<div className="text-start">
											<div className="mb-4">
												<h5 className="fw-bold text-muted">Version</h5>
												<p className="fs-5 fw-bold text-primary">1.0.0</p>
											</div>
											<div className="mb-4">
												<h5 className="fw-bold text-muted">Technology Stack</h5>
												<div className="d-flex flex-wrap gap-2">
													<span className="badge bg-primary px-3 py-2" style={{borderRadius: '20px'}}>React</span>
													<span className="badge bg-success px-3 py-2" style={{borderRadius: '20px'}}>Spring Boot</span>
													<span className="badge bg-info px-3 py-2" style={{borderRadius: '20px'}}>Bootstrap</span>
													<span className="badge bg-warning px-3 py-2" style={{borderRadius: '20px'}}>Axios</span>
												</div>
											</div>
											<div className="mb-4">
												<h5 className="fw-bold text-muted">Description</h5>
												<p className="text-muted">
													A modern, responsive todo list application built with React frontend and Spring Boot backend. 
													Features secure authentication, real-time updates, and an intuitive user interface.
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div className="row mt-5">
							<div className="col-12">
								<div className="card border-0 shadow-lg" style={{borderRadius: '20px', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)'}}>
									<div className="card-body text-center text-white p-5">
										<h3 className="mb-4">🎯 Our Mission</h3>
										<p className="lead mb-4">
											To provide users with a simple, efficient, and beautiful way to organize their tasks and boost productivity.
										</p>
										<div className="row">
											<div className="col-md-4 mb-3">
												<div className="bg-white bg-opacity-20 rounded-3 p-3">
													<h4 className="mb-2 text-dark" style={{fontWeight: '900'}}></h4>
													<p className="mb-0 small text-dark" style={{fontWeight: '900'}}>Beautiful Design</p>
												</div>
											</div>
											<div className="col-md-4 mb-3">
												<div className="bg-white bg-opacity-20 rounded-3 p-3">
													<h4 className="mb-2 text-dark" style={{fontWeight: '900'}}>⚡</h4>
													<p className="mb-0 small text-dark" style={{fontWeight: '900'}}>Fast Performance</p>
												</div>
											</div>
											<div className="col-md-4 mb-3">
												<div className="bg-white bg-opacity-20 rounded-3 p-3">
													<h4 className="mb-2 text-dark" style={{fontWeight: '900'}}></h4>
													<p className="mb-0 small text-dark" style={{fontWeight: '900'}}>Secure & Reliable</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}