console.log("this is script file");
const toggleSidebar= () => {
    if($(".sidebar").is(":visible")){
        //true
        //close sidebar

        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0%");
    } else {
        //false
        //show side bar

        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
    }
};

const searchAdmin=()=>{
    //console.log("searching...")

    let query = $('#search-input').val();
    
    if(query=='')
    {
        $(".search-result").hide();
    } else {
        // search
        console.log(query);

        // sending request to server

        let url=`http://localhost:8080/search/${query}`;

        fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            //data...
            console.log(data);

            let text=`<div class='list-group'>`;

            data.forEach((employee) => {
                text+=`<a href='/admin/${employee.id}/employee' class='list-group-item list-group-item-action'> ${employee.firstName} </a>`
            });

            text+=`</div>`;
            $(".search-result").html(text);
            $(".search-result").show();
        });
    }
};

const searchUser=()=>{
    //console.log("searching...")

    let query = $('#search-input').val();
    
    if(query=='')
    {
        $(".search-result").hide();
    } else {
        // search
        console.log(query);

        // sending request to server

        let url=`http://localhost:8080/search/${query}`;

        fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            //data...
            console.log(data);

            let text=`<div class='list-group'>`;

            data.forEach((employee) => {
                text+=`<a href='/user/${employee.id}/employee' class='list-group-item list-group-item-action'> ${employee.firstName} </a>`
            });

            text+=`</div>`;
            $(".search-result").html(text);
            $(".search-result").show();
        });
    }
};