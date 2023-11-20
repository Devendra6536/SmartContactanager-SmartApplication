const  paymentStart = () =>{
    //let amount = $("#payment_field").val();
    let amount = document.getElementById("payment_field").value;
    if(amount=="" || amount==null){
        alert("Amount is required");
        return;
    }

   


$.ajax(
    {
      url:"/user/create_order",
      data:JSON.stringify({amount:amount,info:"order_request"}),
      contentType:"application/json",
      type:"POST",
      dataType:"json",
      success:function(response){
          if(response.status == "created"){
            console.log("created");
            console.log(response);
            console.log("payment started with "+ response.amount);
            let options={
                key:"rzp_test_8pwtBHd42wsqob",
                amount:response.amount,
                currency:"INR",
                name:"SmartContactManager",
                description:"Donation",
                image:"https://avatars.githubusercontent.com/u/88203724?v=4",
                order_id:response.id,
                handler:function(response){
                    console.log(response.razorpay_payment_id);
                    console.log(response.razorpay_order_id);
                    console.log(response.razorpay_signature);
                    console.log("Payment successfull");
                    alert("successfull");
                },
                prefill: { //We recommend using the prefill parameter to auto-fill customer"s contact information especially their phone number
                    name: "", //your customer"s name
                    email: "",
                    contact: "" //Provide the customer"s phone number for better conversion rates 
                },
                notes: {
                    address: "Smart contact manager"
                },
                theme: {
                    color: "#3399cc"
                }
            };
            let rzp = new Razorpay(options);
            rzp.on("payment.failed", function(response){
                console.log(response.error.code);
                console.log(response.error.description);
                console.log(response.error.source);
                console.log(response.error.step);
                console.log(response.error.reason);
                console.log(response.error.metadata.order_id);
                console.log(response.error.metadata.payment_id);
                alert("Oops payment failed");
            });

            rzp.open();
        }
        else{
            console.log("payment failed");
        }
      },
      error:function(error){
        console.log(amount);
        console.log(error);
        alert("Something went wrong!!");
      }
    }
)

};