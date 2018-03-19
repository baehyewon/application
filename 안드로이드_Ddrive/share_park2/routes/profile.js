var express = require('express');
var mysql = require('mysql');
var router = express.Router();
var connection = mysql.createConnection({   //mysql 연결 rds 주소
  'host' : 'aws-rds.cfsyy1ya3o9o.ap-northeast-2.rds.amazonaws.com',
  'user' : 'user',
  'password' : '2014111614',
  'database' : 'share_park',
});

router.get('/:email', function(req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
    var email= req.body.email;
    var name= req.body.name;

    connection.query('select email, name from user where email=?;',
    	[req.params.email],
    	function(error, cursor){
    		if(error){
    			console.log("DB에러:"+error);
    			res.status(500).json({result:false});
    		}
    		else{
    			if (cursor.length > 0)
    			{
    				res.status(200).json(cursor); // 비밀번호를 제외한 user정보 제공
    			//console.log("프로필 가져왔습니다.", cursor);
    			//res.status(200).json({result:true});
    			}
    			else
    				{
    					res.status(412).json({result : "Unable to find user information."});
    				}
    			}
    		});
});

module.exports = router;