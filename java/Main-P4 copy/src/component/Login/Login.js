import "./Login.css";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";

const Login = (props) => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({});

    const handleChange = (e) => {
        setFormData((prevState) => ({
            ...prevState,
            [e.target.name]: e.target.value,
        }));
    };
    const handleSubmit = (e) => {
        e.preventDefault();
        axios
            .post("http://127.0.0.1:8000/api/login2", {
                username:formData.username,
                password:formData.password
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
            })
            .then((res) => {
                console.log(res)
                if(res.status==200){
                    let token = res.data.id;
                    localStorage.setItem("jwt", token);
                    // props.setLogin(true);
                    navigate("/");
                }else {
                    alert("Usernam and Password Notmatch")
                }
            })
            .catch((err) => {
                alert("Usernam and Password Notmatch")
            });
    };

    return (
        <div className="login-grid-cont">
            <div className={"login-grid"}>
                <div className="login-form-head">
                    <img
                        style={{height: "30vh"}}
                        src={"https://cdn-icons-png.flaticon.com/512/3135/3135715.png"}
                    />
                </div>
                <form className="login-form">
                    <h4>
                        Username :{" "}
                        <input
                            className="detail-input"
                            type={"text"}
                            name={"username"}
                            placeholder={" Username"}
                            onChange={handleChange}
                        />
                    </h4>
                    <br/>
                    <h4>
                        Password :{" "}
                        <input
                            className="detail-input"
                            type={"password"}
                            name={"password"}
                            placeholder={" Password"}
                            onChange={handleChange}
                        />
                    </h4>
                    <p className="signup-btn" onClick={() => navigate("/signup")}>
                        Register
                    </p>
                </form>
                <input
                    className="login-btn"
                    type={"button"}
                    onClick={handleSubmit}
                    value={"Login"}
                />
                <br/>
                <input
                    className="login-btn"
                    type={"button"}
                    onClick={()=>navigate("/")}
                    value={"Buy Page"}
                />
            </div>
        </div>
    );
};

export default Login;
