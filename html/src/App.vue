<template>
  <div class="container">
    <div class="messages">
      <template v-for="message,index in messages">
        <div class="left" v-if='message.role=="assistant"' :key="index">
          <img :src="require('./assets/logo.png')">
          <div class="content">{{message.content}}</div>
        </div>
        <div class="left" :key="index" v-else>
        <img :src="require('./assets/user.png')">
        <div class="content">{{message.content}}</div>
      </div>
      </template>
    </div>
    <div class="operator">
      <input type="text" class="input" v-model="input">
      <div class="button" @click="send">发送</div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'App',
  data(){
    return{
      lock:true,
      input:'',
      messages:[
      ]
    }
  },
  mounted(){
    
  },
  methods:{
    send(){
      if(this.input!=null&&this.input!=''&&this.lock==true){
        this.lock=false;
        this.messages.push({role:"user",content:this.input});
        this.input='';
        let formData = new FormData();
        formData.append("messages",JSON.stringify(this.messages));
        this.$axios({
            url: '/chat',
            method: 'post',
            data: formData
        }).then(res=>{
          console.log(res);
          this.messages.push({role:'assistant',content:res.data});
          this.lock=true;
        })
      }
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
}
img{
  width: 5%;
  height: 5%;
}
.container{
  width: 80%;
  height: 730px;
  margin-left: 10%;
  display: flex;
  flex-direction: column;
}
.messages{
  width: 96%;
  height: 80%;
  margin: 2%;
  overflow: auto;
}
.operator{
  width: 96%;
  height: 16%;
  margin-left: 2%;
}
.left{
  width: 100%;
  padding-top: 5px;
  padding-bottom: 5px;
  border-bottom: 1px solid #DEDEDF;
  background-color: #F7F7F8;
  display: flex;
  flex-direction: row;
}
.left > img{
  margin-left: 2%;
}
.left > .content{
  margin-left: 2%;
  width: 81%;
  padding: 5px;
  word-wrap:break-word;
}
.operator{
  display: flex;
  flex-direction: row;
  align-items: center;
}
.input{
  width: 80%;
  margin-left: 5%;
  margin-right: 5%;
  outline:0;
  padding-left: 10px;
  border:1px solid #c8cccf;
  color:#343541;
  border-radius:4px;  
  font-size: 18px;
  height: 40px;
}
.input:focus{
  outline:none;
  border-color:rgba(93,149,242,.75);
  box-shadow:0 1px 8px rgba(93,149,242,.105);
}
.button{
  cursor: pointer;
  font-size: 18px;
  color: #FFFFFF;
  background-color: #1586DE;
  height: 30px;
  width: 10%;
  line-height: 30px;
  text-align: center;
  padding-top: 7px;
  padding-bottom: 7px;
  border-radius: 2px;
}
.button:hover{
  background-color: #429DE4;
}
</style>
