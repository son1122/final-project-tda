import "./Signup.css";
import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

const Signup = () => {

    const [name, setName] = useState("");
    const [lastName, setLastname] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [prefix, setPrefix] = useState("");
    const [type, setType] = useState("BROKER");
    const [license, setLicense] = useState("");
    const [expire, setExpire] = useState("");
    const navigate = useNavigate();

    function validateForm() {
        if (true) {
            axios
                .post(`http://localhost:8085/agent/register`, {
                    "prefix":prefix,
                    "phone":phone,
                    "lastName":lastName,
                    "name":name,
                    "expire":expire,
                    "type":type,
                    "email":email,
                    "license":license
                }, {
                    headers: {
                        Authorization: `Basic dXNlcjpwYXNzd29yZA==`,
                        'Content-Type': 'application/x-www-form-urlencoded'

                    },

                })
                .then(function (response) {

                    if (response.data.status == "Username has taken") {
                        alert("Username already taken")
                    } else if (response.data == "complete") {
                        navigate("/login");
                    } else {
                        if (
                            "error SequelizeUniqueConstraintError: Validation error" ==
                            response.data
                        ) {
                            alert("User name is already use");
                        } else {
                            alert("error please try again");
                        }
                    }

                })
                .catch(function (error) {
                });
        }
    }

    const signUp = (e) => {
        // e.preventDefault();
        validateForm();
    };
    return (
        <div style={{textAlign: "center", marginTop: "10%"}}>
            <h3>
                Agent Register
            </h3>

            <div style={{
                display: "grid",
                gridTemplateColumns: "1fr 1fr 1fr 1fr",
                margin: "1%",
                marginTop: "3%",
                padding: "3%"
            }}>

                <p>Prefix</p>
                <input onChange={(e) => {
                    setPrefix(e.target.value)
                }
                }/>
                <p>First Name</p>
                <input onChange={(e) => {
                    setName(e.target.value)
                }
                }/>
                <p>Last Name</p>
                <input onChange={(e) => {
                    setLastname(e.target.value)
                }
                }/>
                <p>Phone</p>
                <input onChange={(e) => {
                    setPhone(e.target.value)
                }}/>
                <p>Email</p>
                <input onChange={(e) => {
                    setEmail(e.target.value)
                }}/>
                <p>TYPE</p>
                <select onChange={(e)=>{
                    setType(e.target.value)
                }}>
                    <option>BROKER</option>
                    <option>REPRESENTATIVE</option>
                </select>
                <p>License Id</p>
                <input onChange={(e) => {
                    setLicense(e.target.value)
                }}/>
                <p>Register Id</p>
                <input/>
                <p>Expire Date</p>
                <input type={"date"} onChange={(e) => {
                    setExpire(e.target.value)
                }}/>
            </div>
            <div>
                <button className={"button"} style={{marginLeft: "3%", width: "200px"}} onClick={()=>signUp()}>Register</button>
                <button className={"button"} style={{marginLeft: "3%", width: "200px"}} onClick={()=>navigate("/login")}>Login Page</button>
            </div>
        </div>
    );
};

export default Signup;
