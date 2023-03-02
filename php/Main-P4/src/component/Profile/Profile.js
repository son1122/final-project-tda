import "./Profile.css";
import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {use} from "i18next";
var qs = require('qs');
const Profile = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [firstname, setFirstname] = useState("");
    const [lastname, setLastname] = useState("");
    const navigate = useNavigate();
    const [profilePage, setProfilePage] = useState(1);
    const [lastLogin,setLastLogin]=useState();
    const [expireDate,setExpireDate]=useState()
    const [licenseId,setLicenseId]=useState()
    const [type,setType]=useState()
    const [registerId, setRegisterId] = useState();
    const [prefix, setPrefix] = useState();
    const [form, setForm] = useState({});

    function validateEmail() {
        const regex =
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (regex.test(email)) {
            return true;
        } else {
            alert("Please enter a valid email");
            return false;
        }
    }

    //make sure entered password is correct regex
    function validatePassword() {
        const regex = /^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{8,}$/;
        if (password === confirmPassword) {
            if (regex.test(password)) {
                return true;
            } else {
                alert(
                    "Password must have at least: 1 lowercase, 1 uppercase, 1 number at least 8 character and 1 special character"
                );
                return false;
            }
        } else {
            alert("password not match");
        }
    }

    function validateForm() {
        if (validateEmail() && validatePassword()) {
            axios
                .post(`http://localhost:3010/customer/edit`, {
                    username: username,
                    password: password,
                    email: email,
                    phone: phone,
                    firstname: firstname,
                    lastname: lastname,
                })
                .then(function (response) {
                    console.log(response.data.status);
                    if (response.data.status == "signUp") {
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
                    console.log(error);
                });
        }
    }

    // admin,password,owner@mail.com,000-000-0000
    const getProfile = () => {
        axios.post("http://localhost:8085/agent/profile", {
            auth:localStorage.getItem("jwt")
        },{
            headers: {
                Authorization: localStorage.getItem("jwt"),
                'Content-Type': 'application/x-www-form-urlencoded'

            }}).then(res => {
            // get profile data
            console.log(res.data)
        })
    }
    const Login = () => {
        axios.post("http://localhost:8080/agent/login", {
            username: username,
            password: password
        }).then(res => {
            // id
            console.log(res.data)
            localStorage.setItem("id", res.data.id)
        })
    }
    const signUp = (e) => {
        e.preventDefault();
        validateForm();
    };
    const edit = (e) => {
        // validateEmail()
        // validatePassword()
        axios
            .put(
                `http://localhost:3010/customer/edit`,
                {
                    username: username,
                    password: password,
                    email: email,
                    phone: phone,
                    firstname: firstname,
                    lastname: lastname,
                },{
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
                        'Content-Type': 'application/x-www-form-urlencoded'

                    }}
            )
            .then((res) => {
                console.log(res);
                navigate("/login");
            });
    };
    useEffect(() => {
        axios
            .post(`http://localhost:8085/agent/logindata`, {
                auth : localStorage.getItem("jwt")
            },{
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("jwt")}`,
                    'Content-Type': 'application/x-www-form-urlencoded'

                }})
            .then((res) => {
                console.log(res.data)
                setUsername(res.data.agentUsername)
                setLastLogin(res.data.lastLogin)
                setFirstname(res.data.agentData.name)
                setLastname(res.data.agentData.lastname)
                setEmail(res.data.agentData.email)
                setPhone(res.data.agentData.phone)
                setType(res.data.agentData.type)
                setRegisterId(res.data.agentData.registerId)
                setLicenseId(res.data.agentData.licenseId)
                setPrefix(res.data.agentData.prefix)
                setExpireDate(res.data.agentData.expireDate)
            })
            .catch((err) => {
                console.log(err);
            });
    }, []);
    return (
        <div style={{margin: "5%", backgroundColor: "white"}}>
            {profilePage == 1 ?
                <div>
                    <br/>
                    <h1>User Detail</h1>
                    <div style={{display: "grid", gridTemplateColumns: "1fr 1fr", padding: "10%"}}>
                        <p>Username</p>
                        <input value={username} name={"username"} onChange={(e)=>{setUsername(e.target.value)}}/>
                        <p>Password</p>
                        <input onChange={(e)=>{setPassword(e.target.value)}}/>
                        <p>Last Login</p>
                        <input value={lastLogin} readOnly={true} name={'lastLogin'}/>

                    </div>
                    <div style={{
                        display: "grid",
                        gridTemplateColumns: "1fr 1fr",
                        padding: "1%",
                        paddingLeft: "30%",
                        paddingRight: "30%",
                        marginBottom: "3%"
                    }}>
                        <button className={"button"} onClick={() => {
                            setProfilePage(1)
                        }}>User Detail
                        </button>
                        <button className={"button"} style={{marginLeft: "3%"}} onClick={() => {
                            setProfilePage(2)
                        }
                        }>Profile Detail
                        </button>
                        <button className={"button"} style={{marginTop: "3%"}} onClick={()=>{
                            var data = qs.stringify({
                                auth : localStorage.getItem("jwt"),
                                username:username,
                                password:password
                            });
                            axios
                                .post(`http://localhost:8085/agent/logindata/edit`,{
                                    auth : localStorage.getItem("jwt"),
                                    username:username,
                                    password:password
                                },{
                                    headers: {
                                        Authorization: `Bearer ${localStorage.getItem("jwt")}`,
                                        'Content-Type': 'application/x-www-form-urlencoded'

                                    }}).then(

                            )

                        }
                        }>Edit</button>
                        <button className={"button"} style={{marginTop: "3%", marginLeft: "3%"}} onClick={()=>{
                            localStorage.setItem("jwt",null)
                            navigate("/login")
                        }
                        }>Logout</button>
                    </div>
                </div>
                :
                <div>
                    <h1>Profile</h1>
                    <div style={{
                        display: "grid",
                        gridTemplateColumns: "1fr 1fr 1fr 1fr",
                        margin: "1%",
                        marginTop: "3%",
                        padding: "3%"
                    }}>
                        <p>Prefix</p>
                        <input  type={"text"} value={prefix} name={"prefix"} onChange={(e)=>{setPrefix(e.target.value)}}/>
                        <p>First Name</p>
                        <input value={firstname} name={"name"} onChange={(e)=>{
                            setFirstname(e.target.value)
                        }}/>
                        <p>Last Name</p>
                        <input value={lastname} onChange={(e)=>{setLastname(e.target.value)}}/>
                        <p>Phone</p>
                        <input value={phone} onChange={(e)=>{setPhone(e.target.value)}}/>
                        <p>Email</p>
                        <input value={email} onChange={(e)=>{setEmail(e.target.value)}}/>
                        <p>Type</p>
                        <input value={type} onChange={(e)=>{setType(e.target.value)}}/>
                        <p>License Id</p>
                        <input value={licenseId} onChange={(e)=>{setLicenseId(e.target.value)}}/>
                        <p>Register Id</p>
                        <input value={registerId} onChange={(e)=>{setRegisterId(e.target.value)}}/>
                        <p>Expire Date</p>
                        <input value={expireDate} onChange={(e)=>{setExpireDate(e.target.value)}}/>
                    </div>
                    <div style={{
                        display: "grid",
                        gridTemplateColumns: "1fr 1fr",
                        padding: "1%",
                        paddingLeft: "30%",
                        paddingRight: "30%",
                        marginBottom: "3%"
                    }}>
                        <button className={"button"} onClick={() => {
                            setProfilePage(1)
                        }}>User Detail
                        </button>
                        <button className={"button"} style={{marginLeft: "3%"}} onClick={() => {
                            setProfilePage(2)
                        }
                        }>Profile Detail
                        </button>
                        <button className={"button"} style={{marginTop: "3%"}}>Edit</button>
                        <button className={"button"} onClick={()=>{
                            localStorage.setItem("jwt",null)
                            navigate("/login")
                        }} style={{marginTop: "3%", marginLeft: "3%"}}>Logout</button>
                    </div>
                </div>
            }
        </div>
    );
};

export default Profile;
