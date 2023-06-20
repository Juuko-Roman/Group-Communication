/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


fetch("./client")
        .then(
            function(response){
                if(response.status !== 200){
                    console.log('something went wrong' + response.status);
                    return;
                }
                response.json().then(
                    function(data){
                        console.log('data got is ....'+data);
                    }
                );
            }
        )
        .catch(function(error){
            console.log('fetch error'+error);
        });