<template>
  <div style="height: 100%; width: 100%;">
    <el-container style="width: 100%;height: 100%;align-content: center;">
      <el-main style="width: 60%;height: 100%;">
      <el-row class="first-row">
          <el-col :span="10">
          <el-input v-model="param.num" placeholder="请输入结点个数："></el-input>
        </el-col>
          <el-col :span="14">
          <el-button @click="getImage" type="primary">计算</el-button>
        </el-col>
      </el-row>
        <el-row :gutter="24" class="second-row" style="margin: auto;" v-loading="loading">
          <el-col :span="8" class="second-row-left" v-for="(item, index) in imageUrl">
            <el-card :body-style="{ width: '100%', height: '100%', padding: '0', margin: '0' }">
              <el-image
              :src="imageUrl[index]"
              :preview-src-list="previewList"
              :initial-index="index"
              @click="vbs(imageUrl[index])">
                <template #error>
                  <div class="image-slot">
                    <el-empty />
                  </div>
                </template>
              </el-image>
              
              <div style="text-align: center;">
                <span>Yummy hamburger</span>
              </div>
            </el-card>
          </el-col>
      </el-row>
    </el-main>
  </el-container>
  </div>
</template>
<script>
import API from "../api/api_file"
export default {
  created() {
    //this.getImage();
    if(this.imageUrl.length === 0) {
      this.loading = true;
    }
  },
  data() {
    return {
      param: {
        num: null,
      },
      loading: false,
      imageUrl: [
         //"https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
      ],
      previewList:[
      "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
      ],
      showImage: false
    }
  },
  methods: {
    getImage() {
      var that = this;
      this.loading = true;
      API.getImage(this.param).then(
        function (res) {
          var base64 = res.data;
          for (let index = 0; index < base64.length; index++) {
            const element = "data:image/png;base64," + base64[index];
            that.imageUrl.push(element);
            that.loading = false;
          }
        }
      )
    },
    vbs(val) {
      this.previewList = []
      this.previewList.push(val)
    }
  }
}
</script>

<style scoped>
.first-row {
  width: 40%;
  height: 10%;
  align-items: center;
  text-align: center;
  margin: auto;
}

.first-row-left {
  width: 50%;
  height: 100%;
  align-items: center;
}

.first-row-left .el-input {
  align-items: center;
}

.first-row-right {
  width: 50%;
  height: 100%;
  align-items: center;
}

.first-row-right .el-button {
  align-items: center;
}

.second-row {
  width: 80%;
  height: fit-content;
  min-height: 40%;
  margin: auto;
  align-content: center;
  border: 1px solid blue;
}

.second-row-left {
  height: 100%;
  padding: 10px 0;
  align-content: center;
  border: 1px solid black;
}

.el-col .el-card {
  height: 100%;
  background-color: aliceblue;
}

.el-card :deep(.el-empty__description) {
  display: none !important;
}

.el-image {
  width: 90%;
  height: 90%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  padding: 0;
}
.el-image img {
  margin: 0 auto;
}

</style>
