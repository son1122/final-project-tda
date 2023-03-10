import "./buy.css"
import {useEffect, useState} from "react";
import axios from "axios";

const Buy = (props) => {
    const [buyView, setBuyView] = useState(0)
    const [year, setYear] = useState("")
    const [brand, setBrand] = useState("")
    const [model, setModel] = useState("")
    const [carYearData, setCarYearData] = useState()
    const [carBrandData, setCarBrandData] = useState()
    const [carModelData, setCarModelData] = useState()
    const [sex,setSex]=useState("")
    const [capital,setCapital]=useState("")
    const [family,setFamily]=useState("")
    const [plate,setPlate]=useState("")
    const [province,setProvince]=useState("")
    const [type,setType]=useState("")
    const [customerId, setCustomerId] = useState(0);

    const [DOB, setDOB] = useState();
    const [Benificial, setBenificial] = useState();


    const today = new Date();
    const numberOfDaysToAdd = 1;
    const dateAdd = today.setFullYear(today.getFullYear() + numberOfDaysToAdd);
    const defaultValue = new Date(dateAdd).toISOString().split('T')[0]
    const defaultValue1 = new Date().toISOString().split('T')[0]
    const [startDate, setStartDate] = useState(defaultValue1);
    const [endDate, setEndDate] = useState(defaultValue);
    // const [diff, setDiff] = useState(1);

    // Customer Data
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [firstname, setFirstname] = useState();
    const [lastname, setLastname] = useState();
    const [prefix, setPrefix] = useState();
    const [govermentId, setGovermentId] = useState("");
    //Location Data
    const [address, setAddress] = useState("");
    const [subDistrict, setSubDistrict] = useState("");
    const [district, setDistrict] = useState("");
    const [customerProvince, setCustomerProvince] = useState("");
    const [zip, setZip] = useState("");


    const buyCar =()=>{
        if(province==""||plate==""||type==""){
            alert("Pleas fill Plate and Province Data and type")
            return
        }
        axios
            .post(`http://localhost:3010/customer/newinsure`, {
                year:year,
                brand:brand,
                model:model,
                plate:plate,
                province:province,
                type:parseInt(type),
            },{
                headers: { Authorization: `Bearer ${localStorage.getItem("jwt")}` },
            })
            .then(res=>{
                if(res.status==200){
                    alert("Complete Transaction Insurance Price is "+res.data.price+" with "+Math.round((1-res.data.discount)*100)+" % Discount")
                    props.setView(0)
                }
            }).catch(e=>alert("error"))
    }
    // const
    useEffect(() => {
        setBuyView(JSON.parse(window.localStorage.getItem('buyView')));
    }, []);

    useEffect(() => {
        window.localStorage.setItem('buyView', buyView);
    }, [buyView]);
    const handleChangeSelectType = (e) => {
        setType(e.target.value);
    };
    const handleChangeSelectYear = (e) => {
        setYear(e.target.value);
    };
    const handleChangeSelectBrand = (e) => {
        setBrand(e.target.value);
    };
    const handleChangeSelectModel = (e) => {
        setModel(e.target.value);
    };
    // Car Year
    useEffect(() => {
        const select = axios
            .get("http://localhost:3010/customer/getcaryear", {
                headers: {Authorization: `Bearer ${localStorage.getItem("jwt")}`},
            })
            .then((resu) => {
                console.log(resu);
                let data = resu.data.map((name, index) => {
                    console.log(name.year)
                    return (
                        <option value={name.year} key={index}>
                            {name.year}
                        </option>
                    );
                });
                setCarYearData(data)
            })
    }, [])
    useEffect(() => {
        const select = axios
            .get("http://localhost:3010/customer/getcarmodel/" + year, {
                headers: {Authorization: `Bearer ${localStorage.getItem("jwt")}`},
            })
            .then((resu) => {
                console.log(resu);
                let data = resu.data.map((name, index) => {
                    console.log(name.brand)
                    return (
                        <option value={name.brand} key={index}>
                            {name.brand}
                        </option>
                    );
                });
                setCarBrandData(data)
            })
    }, [year])
    useEffect(() => {
        const select = axios
            .get("http://localhost:3010/customer/getcarmodel/" + year + "/" + brand, {
                headers: {Authorization: `Bearer ${localStorage.getItem("jwt")}`},
            })
            .then((resu) => {
                console.log(resu);
                let data = resu.data.map((name, index) => {
                    console.log(name.model)
                    return (
                        <option value={name.model} key={index}>
                            {name.model}
                        </option>
                    );
                });
                setCarModelData(data)
            })
    }, [brand])
    const setdata=()=>{
        localStorage.setItem("firstname",firstname)
        localStorage.setItem("lastname",lastname)
        localStorage.setItem("email",email)
        localStorage.setItem("prefix",prefix)
        localStorage.setItem("email",email)
        localStorage.setItem("address",address)
        localStorage.setItem("subdistrict",subDistrict)
        localStorage.setItem("district",district)
        localStorage.setItem("province",province)
        localStorage.setItem("zip",zip)
        localStorage.setItem("gid",govermentId)
        localStorage.setItem("save",1)
        localStorage.setItem("phone",phone)
    }


    useEffect(() => {
        let id = customerId

        axios.post("http://localhost:8085/insurance/getuser",{
            'id': '123123',
            'type': 'test11',
            'registerId': id
        },{
            headers: {
                'Authorization': 'Basic dXNlcjpwYXNzd29yZA==',
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(resp=>{
            setPrefix(resp.data.prefix)
            setFirstname(resp.data.name)
            setLastname(resp.data.lastname)
            setPhone(resp.data.phone)
            setGovermentId(resp.data.govermentId)
            setAddress(resp.data.locationDatas[0].address)
            setSubDistrict(resp.data.locationDatas[0].subDistrict)
            setDistrict(resp.data.locationDatas[0].district)
            setProvince(resp.data.locationDatas[0].province)
            setZip(resp.data.locationDatas[0].zipcode)

        })

        return () => {

        };
    }, [buyView]);


    const [life, setLife] = useState();
    const [selectLife, setSelectLife] = useState({
        coverage:500000,
        premium:7200,
        detail:"Policy Type: Term Life Insurance Policy Term: 20 years Death Benefit: $500,000 Premium: $50 per month or $600 per year"
    });

    useEffect(() => {
        axios.get("http://localhost:8085/insurance/life").then((resp)=>{
            console.log(resp.data)
            let data = resp.data.map(loop=>{
                return (
                    <div>
                        <div style={{display:"grid",gridTemplateColumns:"5fr 1fr 1fr 1fr"}}>
                            Title : Life Insurance {loop.id}
                            <p>Coverage Cost</p>
                            <p>Premium</p>
                            <p>Select Button</p>
                        </div>
                        <div style={{display:"grid",gridTemplateColumns:"5fr 1fr 1fr 1fr"}}>
                            <p>{loop.detail}</p>
                            <p>{loop.coverage}</p>
                            <p>{loop.premium}</p>
                            <button onClick={(e)=>{setSelectLife(loop)}}>SELECT</button>
                        </div>
                    </div>
                )
            })
            setLife(data)
        })
        return () => {

        };
    }, []);


    return (
        <div className={"buy-grid"}>
            <div className={"buy-grid-icon"}>
                <button onClick={() => setBuyView(0)}>Car Insurance</button>
                <button onClick={() => setBuyView(1)}>Health Insurance</button>
                <button onClick={() => setBuyView(2)}>Life Insurance</button>
                <button onClick={() => setBuyView(3)}>Property Insurance</button>
            </div>
            <div className={"buy-body buy-scroll"}>
                {buyView == 0 && <div className={""}>
                    <h1>Car Insurance Type</h1>
                    <select name="<OrderID>" id="OrderID" onChange={handleChangeSelectType}>
                        <option value="" selected disabled hidden>
                            Please Select
                        </option>
                        <option value={1} key={1}>
                            Type 1
                        </option>
                        <option value={2} key={2}>
                            Type 2
                        </option>
                        <option value={3} key={3}>
                            Type 3
                        </option>
                    </select>
                    <p>Car Plate</p>
                    <input type={"text"} placeholder={"Plate Number"}  onChange={(e) => {
                        setPlate(e.target.value);
                    }}/>
                    <p>Province</p>
                    <input type={"text"} placeholder={"Province"} onChange={(e) => {
                        setProvince(e.target.value);
                    }}/>
                    <h2>Car Data</h2>
                    {(buyView == 0) && <div>
                        <h3>Please Select Car Year</h3>
                        <select name="<OrderID>" id="OrderID" onChange={handleChangeSelectYear}>
                            <option value="" selected disabled hidden>
                                Please Select
                            </option>
                            {carYearData}
                        </select>
                    </div>}
                    {(buyView == 0 & year != "") ? <div>
                        <h3>Please Select Car Brand</h3>
                        <select name="<OrderID>" id="OrderID" onChange={handleChangeSelectBrand}>
                            <option value="" selected disabled hidden>
                                Please Select
                            </option>
                            {carBrandData}
                        </select>
                    </div> : <></>}
                    {(buyView == 0 & brand != "") ? <div>
                        <h3>Please Select Car Model</h3>
                        <select name="<OrderID>" id="OrderID" onChange={handleChangeSelectModel}>
                            <option value="" selected disabled hidden>
                                Please Select
                            </option>
                            {carModelData}
                        </select>
                    </div> : <></>}
                    {(buyView == 0 & model != "") ? <div>
                        <h2>personal data</h2>
                        <form>
                            <h4>SELECT GENDER</h4>
                            <input type="radio" name="gender" id="male" onClick={()=>setSex("man")}/>
                            <label htmlFor="male">Male</label>
                            <input type="radio" name="gender" id="female" onClick={()=>setSex("female")}/>
                            <label htmlFor="female">Female</label>
                            <input type="radio" name="gender" id="female" onClick={()=>setSex("other")}/>
                            <label htmlFor="female">LGBTQ</label>
                            <br/>
                            <h4>Capital</h4>
                            <input type="radio" name="capital" id="male" onClick={()=>setCapital("true")}/>
                            <label htmlFor="male">Drive in Capital</label>
                            <input type="radio" name="capital" id="female" onClick={()=>setCapital("false")}/>
                            <label htmlFor="female">Out of Town</label>
                            <br/>
                            <h4>Family</h4>
                            <input type="radio" name="family" id="male"  onClick={()=>setFamily("single")}/>
                            <label htmlFor="male">Single</label>
                            <input type="radio" name="family" id="female" onClick={()=>setFamily("marry")}/>
                            <label htmlFor="female">Marry</label>
                            <input type="radio" name="family" id="female" onClick={()=>setFamily("kid")}/>
                            <label htmlFor="female">Marry with Kids</label>
                            <br/>
                            <br/>
                        </form>
                    </div> : <></>}
                    {(buyView == 0 & family != ""& capital!=""&sex!="") ? <div>
                        <button onClick={buyCar}>Submit</button>
                        <br/>
                        <br/>
                        <br/>
                    </div>:<></>}
                </div>}

                {buyView == 1 && <div>Health Insurance</div>}
                {buyView == 2 && <div>
                    <h1>
                        Life Insurance Selling Page
                    </h1>
                    <div>
                        {life}
                    </div>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(21)}>Next</button>
                </div>}
                {buyView == 21 && <div>
                    <h1>
                        Insurance Detail
                    </h1>
                    <h2>
                        Add customer Register ID
                    </h2>
                    <input onChange={(e) => {
                        setCustomerId(e.target.value)
                    }} />
                    <div>
                        Coverage Detail <br/>
                        <input value={selectLife.detail} readOnly={"true"} style={{height:"100px",width:"800px"}}/><br/>
                        Coverage Cost <br/>
                        <input value={selectLife.coverage} readOnly={"true"} style={{height:"100px",width:"800px"}}/><br/>
                        Total Price <br/>
                        <input value={selectLife.premium} readOnly={"true"}/><br/>
                    </div>
                    <br/>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{
                        setBuyView(2)
                    }}>BACK</button>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{
                        setBuyView(22)
                    }}>NEXT</button>
                </div>}
                {buyView == 22 && <div>
                    <h1>
                        Customer Data
                    </h1>
                    <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",textAlign:"left",paddingLeft:"10%"}}>
                        <div>

                            Prefix <input value={prefix} onChange={(e)=>{setPrefix(e.target.value)}}/><br/><br/>
                            First name <input value={firstname} onChange={(e)=>{setFirstname(e.target.value)}}/><br/><br/>
                            Last name <input value={lastname} onChange={(e)=>{setLastname(e.target.value)}}/><br/><br/>
                            Phone <input value={phone} onChange={(e)=>{setPhone(e.target.value)}}/><br/><br/>
                            Goverment Id <input value={govermentId} onChange={(e)=>{setGovermentId(e.target.value)}}/><br/><br/>
                            Start Insurance <input type={"date"} onChange={(e)=>{setStartDate(e.target.value)}} value={startDate}/><br/><br/>
                            Date of birth <input type={"date"} onChange={(e)=>setDOB(e.target.value)}/><br/><br/>
                        </div>
                        <div>
                            Address <input value={address} onChange={(e)=>{setAddress(e.target.value)}}/><br/><br/>
                            Sub District <input value={subDistrict} onChange={(e)=>{setSubDistrict(e.target.value)}}/><br/><br/>
                            District <input value={district} onChange={(e)=>{setDistrict(e.target.value)}}/><br/><br/>
                            Province <input value={province} onChange={(e)=>{setProvince(e.target.value)}}/><br/><br/>
                            zipcode <input value={zip} onChange={(e)=>{setZip(e.target.value)}}/><br/><br/>
                            Start Insurance <input type={"date"} onChange={(e)=>{setEndDate(e.target.value)}} value={endDate}/><br/><br/>
                            Benificial <input onChange={(e)=>setBenificial(e.target.value)}/><br/><br/>
                        </div>
                    </div>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{
                        setdata()
                        setBuyView(21)



                    }}>BACK</button>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setdata()}>Save</button>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{
                        setFirstname(localStorage.getItem("firstname"))
                        setLastname(localStorage.getItem("lastname"))
                        setEmail(localStorage.getItem("email"))
                        setPrefix(localStorage.getItem("prefix"))
                        setAddress(localStorage.getItem("address"))
                        setSubDistrict(localStorage.getItem("subdistrict"))
                        setDistrict(localStorage.getItem("district"))
                        setProvince(localStorage.getItem("province"))
                        setZip(localStorage.getItem("zip"))
                        setGovermentId(localStorage.getItem("gid"))
                        setPhone(localStorage.getItem("phone"))
                        setLife(localStorage.getItem("life"))

                    }}>Get Save</button>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{

                        setBuyView(50)
                    }}>next</button>
                </div>}
                {buyView == 23 && <div>
                    <h1>
                        Thank you for choosing Dhipaya Insurance
                    </h1><br/><br/>
                    <div>
                        <h2>
                            Your Insurance has been send to customer Email
                        </h2>
                    </div>
                    {/*<button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(52)}>next</button>*/}
                </div>}
                {buyView == 52 && <div>
                    <h1>
                        Insurance Detail
                    </h1>
                    <div>
                        <h2>Please click to buy</h2>
                    </div>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(50)}>BUY</button>
                </div>}

                {buyView == 3 && <div>
                    <h1>
                        Property Insurance Selling Page
                    </h1>
                    <h3>Please Select Province of Property</h3>
                    <select name="<OrderID>" id="OrderID">
                        <option value="" selected disabled hidden>
                            Please Select
                        </option>
                        {carYearData}
                    </select>
                    <h3>Please Select district of Property</h3>
                    <select name="<OrderID>" id="OrderID">
                        <option value="" selected disabled hidden>
                            Please Select
                        </option>
                        {carYearData}
                    </select>
                    <h3>Please Select sub district of Property</h3>
                    <select name="<OrderID>" id="OrderID">
                        <option value="" selected disabled hidden>
                            Please Select
                        </option>
                        {carYearData}
                    </select>
                    <h3>Please Provide Property length</h3>
                    <div>
                        width <input/><br/><br/>
                        long <input/>
                    </div>
                    <br/>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(31)}>Next</button>
                </div>}
                {buyView == 31 && <div>
                    <h1>
                        Insurance Detail
                    </h1>
                    <div>
                        Coverage Detail <br/>
                        <input value={"test"} readOnly={"true"} style={{height:"100px",width:"800px"}}/><br/>
                        Coverage Cost <br/>
                        <input value={"test"} readOnly={"true"} style={{height:"100px",width:"800px"}}/><br/>
                        Detail <br/>
                        <input value={"test"} readOnly={"true"} style={{height:"100px",width:"800px"}}/><br/>
                        Total Price <br/>
                        <input value={"test"} readOnly={"true"}/><br/>
                    </div>
                    <br/>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(32)}>Next</button>
                </div>}
                {buyView == 32 && <div>
                    <h1>
                        Customer Data
                    </h1>
                    <div>
                        Address <input/><br/><br/>
                        Sub District <input/><br/><br/>
                        District <input/><br/><br/>
                        Province <input/><br/><br/>
                        zipcode <input/><br/><br/>
                        Goverment Id <input/><br/><br/>
                    </div>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(50)}>next</button>
                </div>}
                {buyView == 50 && <div>
                    <div>
                        <div>
                            <style dangerouslySetInnerHTML={{__html: "\n        body {\n            background: rgb(204, 204, 204);\n        }\n        .a4 {\n            background: white;\n            display: block;\n            margin: 0 auto;\n            margin-bottom: 0.5cm;\n            box-shadow: 0 0 0.5cm rgba(0, 0, 0, 0.5);\n            width: 21cm;\n            height: 29.7cm;\nmargin-right: 1cm;\nmargin-left: 1cm;\n        }\n    " }} />
                            <div className="a4">
                                <div style={{display: 'grid', gridTemplateRows: '1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr', height: '100%'}}>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '2fr 1fr 6fr 2fr', alignItems: 'center'}}>
                                        <p style={{fontSize: '0.02em', textAlign: 'center'}}>????????????????????????????????????????????????????????????????????? <br />1115 ????????? ?????????????????? 3 ??????????????????????????????????????? <br />?????????????????????????????? ????????????????????? 10120 <br />???????????????????????? 1736 0 2230 2200 <br />?????????????????? 02230 2040 <br />?????????????????????????????????????????????????????????????????? <br />0007539000533</p>
                                        <img style={{height: '80px', textAlign: 'center'}} src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKRxpwFRKAno71QExzA6t51o8g6ftoqGISXJ89jsOaWEFmeruNh2kLhuhl59t_QoiPXa0&usqp=CAU" />
                                        <div style={{display: 'grid', gridTemplateRows: '1fr 1fr', textAlign: 'center'}}>
                                            <h3 style={{marginBlockEnd: '0.1em'}}>?????????????????? ??????????????????????????????????????? ??????????????? (???????????????)</h3>
                                            <h4 style={{marginBlockStart: '0.7em'}}>DHIPAYA INSURANCE PUBLIC COMPANY LIMITED</h4>
                                        </div>
                                        <p style={{fontSize: '0.02em', textAlign: 'center'}}>HEAD OFFICE ADDRESS <br />1115 RAMA3 ROAD, CHONGNGONSE <br /> BANGKOK 10120 <br /> TEL 1736, 0 2233 2200<br />FAX 0 2233 2240<br />www.dhipaya.co.th</p>
                                    </div>
                                    <div style={{textAlign: 'center'}}>
                                        <h3>????????????????????????????????????????????????????????? (???????????????????????????????????????????????????????????????????????????????????? (Online))<br />THE SCHEDULE <br />????????????????????????????????????????????????????????????????????? </h3>
                                    </div>
                                    <div style={{display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                        <p style={{marginLeft: '20px'}}>?????????????????????????????? : DHP<br />Company Code : DHP</p>
                                        <p>????????????????????????????????????????????????????????????????????? : 999999999 <br />Policy No. : 99999999999</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                        <p style={{fontSize:"0.8em"}}>????????????????????????????????????????????? : ?????????????????????????????????????????? : {firstname +"  "+lastname}<br />The Insured : Name and Address {firstname +"  "+lastname}:</p>
                                        <p>?????????????????????????????????????????????????????? : ID No. {govermentId}<br /> ??????????????? : Occupation : <br />???????????? : Age <br />??????????????????????????????????????? : Telephone No. : {phone}</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                        <p>???????????????????????????????????????????????? : ?????????????????????????????????????????? : <br />The Beneficiary : Name and Address :</p>
                                        <p>?????????????????????????????????????????????????????????????????????????????????????????? : ??????????????? <br />Relationship to the Insured : Mother</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr'}}>
                                        <p style={{fontSize: '0.2em'}}>??????????????????????????????????????????????????? 1 ?????? <br />Period of Insurance </p>
                                        <p style={{fontSize: '0.2em'}}>: ??????????????????????????????????????????<br />: From</p>
                                        <p style={{fontSize: '0.2em'}}>1</p>
                                        <p style={{fontSize: '0.2em'}}>????????????<br />at</p>
                                        <p style={{fontSize: '0.2em'}}>{startDate}</p>
                                        <p style={{fontSize: '0.2em'}}>???.<br />hours</p>
                                        <p style={{fontSize: '0.2em'}}>???????????????????????????????????????<br /> To</p>
                                        <p style={{fontSize: '0.2em'}}>{endDate}</p>
                                        <p style={{fontSize: '0.2em'}}>???.<br />hours</p>
                                    </div>
                                    <div style={{marginLeft: '20px'}}>
                                        <p>?????????????????????????????????????????????????????????(Insuring Agreement/Endosement)</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                        <p style={{textAlign: 'center'}}>?????????????????????????????????????????????????????????<br />(Insuring Agreement/Endosement)</p>
                                        <p style={{textAlign: 'center'}}>???????????????????????????????????????????????????????????????<br />Sum Insured (Bath)</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                        <p>{selectLife.detail}</p>
                                        <p>{selectLife.coverage}</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr'}}>
                                        <p style={{fontSize:"0.9em"}}>????????????????????????????????????????????????????????? : {selectLife.premium} ?????????<br />Net Premium :     Bath</p>
                                        <p style={{fontSize:"0.9em"}}>?????????????????????????????? : 20 ?????????<br />Stamps Duty:   Bath</p>
                                        <p style={{fontSize:"0.9em"}}>????????????????????????????????????????????? : {Math.round(selectLife.premium*0.07)} ????????? <br /> VAT Bath</p>
                                        <p style={{fontSize:"0.9em"}}>??????????????????????????????????????????????????? {selectLife.premium*1.07+20} ?????????<br />Total Premium  Bath</p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr'}}>
                                        <p style={{fontSize: '0.4em'}}><input type="checkbox" className="onoffswitch-checkbox" defaultChecked="true" /> ????????????????????????????????????????????? Direct</p>
                                        <p style={{fontSize: '0.4em'}}><input type="checkbox" className="onoffswitch-checkbox" defaultChecked="true" /> ???????????????????????????????????????????????????????????? Agent</p>
                                        <p style={{fontSize: '0.4em'}}><input type="checkbox" className="onoffswitch-checkbox" defaultChecked="true" /> ????????????????????????????????????????????????????????????????????????????????? Broker</p>
                                        <p style={{fontSize: '0.4em'}}>????????????????????????????????????????????? : <br />License No. : </p>
                                    </div>
                                    <div style={{marginLeft: '20px'}}>
                                        <div style={{display: 'grid', gridTemplateColumns: '1fr 1fr'}}>
                                            <p>??????????????????????????????????????????????????????????????? : <br />Agreement made on : </p>
                                            <p>????????????????????????????????????????????????????????????????????? : <br />Policy issued on : </p>
                                        </div>
                                        <p style={{fontSize: '0.4em'}}>???????????????????????????????????????????????? ?????????????????? ?????????????????????????????????????????????????????????????????????????????????????????? ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????? ??? ???????????????????????????????????????????????????<br />
                                            As evidence the Company has caused this policy to be signed by duty authorized persons and the Company's stamp to be affored at its office
                                        </p>
                                    </div>
                                    <div style={{marginLeft: '20px', display: 'grid', gridTemplateColumns: '1fr 1fr 1fr 1fr'}}>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <h1>Please Click to BUY</h1>
                    </div>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>setBuyView(22)}>Back</button>
                    <button style={{backgroundColor:"#ff2531",color:"white",width:"200px",height:"40px"}} onClick={()=>{setBuyView(50)
                        axios.post("http://localhost:8085/insurance/buy",{
                            customerId:customerId,
                            name:firstname,
                            lastname:lastname,
                            prefix:prefix,
                            phone:phone,
                            email:"wongsatorn.no@hotmail.com",
                            dob:DOB,
                            beneficial:Benificial,
                            address:address,
                            subDistrict:subDistrict,
                            district:district,
                            province:province,
                            zipcode:zip,
                            startDate:startDate,
                            endDate:endDate,
                            auth:localStorage.getItem("jwt"),
                            registerId:customerId,
                            detail:selectLife.detail,
                            premium:selectLife.premium,
                            coverage:selectLife.coverage,
                            GID:1111,

                        },{
                            headers: {
                                'Authorization': 'Basic dXNlcjpwYXNzd29yZA==',
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        }).then(resp=>{
                            localStorage.setItem("save",0)
                        })
                        setTimeout(function() {
                            window.open("http://localhost:8085/pdf")
                        }, 5000);

                    }}>BUY</button>

                </div>}
            </div>
        </div>
    );
};

export default Buy
