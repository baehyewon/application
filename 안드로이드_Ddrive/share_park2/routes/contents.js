var express = require('express');
var mysql = require('mysql');
var router = express.Router();
var connection = mysql.createConnection({   //mysql 연결 rds 주소
  'host' : 'app.cevmko3n2k3e.ap-northeast-2.rds.amazonaws.com',
  'user' : 'user',
  'password' : 'Moon1102',
  'database' : 'Sharewith',
});

/*
 * Method       : GET
 * Path         : http://52.78.20.203:3000/cate/add
 * Description  : 카테고리를 추가합니다.
 */

 //post 생성
router.post('/create', function (req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
  var post_cate = req.body.post_cate;
  var post_title = req.body.post_title;
  var post_user = req.body.post_user;
  var post_date = req.body.post_date;
  var post_content = req.body.post_content;
  var post_star = req.body.post_star;
  var post_file = req.body.post_file;
  var post_img = req.body.post_img;
  var id = req.body.id;


  connection.query('insert into post (post_cate, post_title, post_user, post_date, post_content, post_star, post_file, post_img) values (?, ?, ?, ?, ?, ?, ?, ?);',
    [post_cate, post_title, post_user, post_date, post_content, post_star, post_file, post_img],
    function(error, cursor){
      if(!error){
        console.log("post추가 성공");
        res.status(200).json({result: true});
        //
      }else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
  });

//post삭제
router.post('/delete', function (req, res, next) { 
  var post_pk = req.body.post_pk;
  var post_user = req.body.post_user;
   
  connection.query('select * from post where post_pk=? and post_user=?;',
    [post_pk, post_user],
    function (error, cursor) {
      if (!error) {
        if (!(cursor.length == 0)) { //id 주인이 포스트를 만든것이 맞는 상황 
            connection.query('delete from post where post_pk=?;',
                             [post_pk],
                             function (error, cur) {
                              if(!error){
                                console.log("post삭제 성공");
                                res.status(200).json({result: true});
                              }
                              else{
                                console.log("post삭제 실패!!\n" + error);
                                res.status(404).json({result: false});
                              }
                            });
          } else {
          console.log("사용자가 만든 포스트가 아니기 때문에 삭제할 수 없습니다.");
          res.status(405).json({result: false});
            }
      } else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
});

//post내용수정
router.post('/edit_content', function (req, res, next) {
  var post_pk = req.body.post_pk;
  //var post_title = req.body.post_title;
  var post_user = req.body.post_user;
  var post_content = req.body.post_content;
   
  connection.query('select * from post where post_pk=? and post_user=?;',
    [post_pk, post_user],
    function (error, cursor) {
      if (!error) {
        if (!(cursor.length == 0)) { //id 주인이 포스트를 만든것이 맞는 상황 
      
            connection.query('update post set post_content=? where post_pk=?;',
                             [post_content, post_pk],
                             function (error, cur) {
                              if(!error){
                                console.log("post내용수정 성공");
                                res.status(200).json({result: true});
                              }
                              else{
                                console.log("post내용수정 실패!!\n" + error);
                                res.status(404).json({result: false});
                              }
                            });
          } else {
          console.log("사용자와 포스트가 일치하지 않습니다.");
          res.status(405).json({result: false});
            }
      } else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
});

module.exports = router;