import React, { Component } from 'react'

class Header extends Component {
    
    constructor(props){
    super(props)
   
    this.state = {

    }

}



  render() {
    return (
        <div>
            <header>
                <nav className='navbar navbar-expand-md navbar-dark bg-dark'>
                <div className='container-fluid'>
                    <a href="../App.js" className="navbar-brand">TekGain</a>
                    </div>
                    <div className="navbar-nav ml-auto d-flex">
             <a href='/course' style={{ textDecoration: 'none' }}><button className="nav-link btn btn-primary mr-2">Course</button></a>
             <a href='/associate' style={{ textDecoration: 'none' }}><button className="nav-link btn btn-primary mr-2">Associate</button></a>
             <a href='/admission' style={{ textDecoration: 'none' }}><button className="nav-link btn btn-primary">Admission</button></a>
            </div>
        
                </nav>

            </header>

        </div>
      
    )
  }
}

export default Header;
