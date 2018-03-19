var express = require('express');
var mysql = require('mysql');
var router = express.Router();
var connection = mysql.createConnection({   //mysql 연결 rds 주소
  'host' : 'aws-rds.cfsyy1ya3o9o.ap-northeast-2.rds.amazonaws.com',
  'user' : 'user',
  'password' : '2014111614',
  'database' : 'share_park',
});

/*
 * Method       : POST
 * Path         : http://localhost:3000/login
 * Description  : 로그인을 시도합니다.
 */

router.post('/', function(req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
    var email= req.body.email;
    var password= req.body.password;

    connection.query('select * from user where email=?;',
                    [email],
                    function(error, cursor){
        if(!error){
            if((!cursor.length==0)&&(cursor[0].password==password))
                {
                    //debug
                    console.log("로그인성공", cursor);
                    res.status(200).json({result:true});
                }
            else if(cursor.length==0){
                //debug
                console.log("계정이 존재하지 않는다.", cursor);
                res.status(405).json({result:false});
            }
            else {
                //debug
                console.log("아이디/비밀번호 오류", cursor);
                res.status(401).json({result:false});
            }
        }
        else{
            //debug
            console.log("DB에러:"+error);
            res.status(404).json({result:false});
          }
    });
});

module.exports = router;