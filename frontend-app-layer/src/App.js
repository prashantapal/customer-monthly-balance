import React, { useState } from "react"
import ReactDOM from "react-dom";
import AccountStatementAction from "./action/AccountStatementAction"
import logo from "./image/logo.jpg"
import "./style/accountStatement.css"

function App() {

  return(
    <>
      <div className="header">
        <img src={logo} alt="HCDL Bank Logo" />
      </div>
    <AccountStatementAction />
    </>
  )
}

export default App;