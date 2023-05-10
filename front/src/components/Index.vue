<template>
  <div style="height: 100%; width: 100%;">
    <el-container style="width: 100%;height: 100%;align-content: center;">
      <el-main style="width: 60%;height: 100%;">
        <el-row class="first-row">
          <el-col :span="10">
            <el-input v-model="param.num" placeholder="请输入结点个数："></el-input>
          </el-col>
          <el-col :span="14">
            <el-button @click="getImage" type="primary">Calculate</el-button>
          </el-col>
        </el-row>
        <el-row :gutter="24" class="second-row" style="margin: auto;" v-loading="loading">
          <el-col :span="8" class="second-row-left" v-for="(item, index) in imageUrl">
            <el-card :body-style="{ width: '100%', height: '100%', padding: '0', margin: '0' }">
              <el-image :src="imageUrl[index]" :preview-src-list="previewList" :initial-index="index"
                @click="vbs(imageUrl[index])">
                <template #error>
                  <div class="image-slot">
                    <el-empty />
                  </div>
                </template>
              </el-image>

              <div style="text-align: center;" v-if="index != 0">
                <span>可信信息覆盖面积：{{ areas[index] }}</span>
              </div>
              <div style="text-align: center;" v-else>
                <span>初试状态</span>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
    <div style="width: 150px;height: 200px;position: fixed;top: calc(10% + 20px);left: 10px;">
      <div style="display: flex;align-items: center;margin-bottom: 20px;" v-for="(item, index) in iconConfig">
        <div style="width: 80px;justify-content: center;display: flex;">
          <template v-if="item.type == 1">
            <div style="width: 15px;height: 15px; border-radius: 50%;" :style="{background: item.color}"></div>
          </template>
          <template v-else-if="item.type == 2">
            <div :style="{color: item.color}"><span class="iconfont" style="font-size: 25px;">&#xe647;</span></div>
          </template>
          <template v-else-if="item.type == 3">
            <div :style="{borderBottom: `3px dashed ${item.color}`, width: '30px'}"></div>
          </template>
          <template v-else-if="item.type == 4">
            <div style="width: 30px;height: 30px;border: 1px dashed black;"></div>
          </template>
          <template v-else-if="item.type == 5">
            <div :style="{color: item.color, fontSize: '30px'}">*</div>
          </template>
        </div>
        <div :style="{color: item.color, fontSize: '12px'}" style="flex: 1;text-align: center;">{{ item.name }}</div>
      </div>
    </div>
  </div>
</template>
<script>
import API from "../api/api_file"
export default {
  created() {
    //this.getImage();
    // if (this.imageUrl.length === 0) {
    //   this.loading = true;
    // }
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
      areas: [0,1,2,3,4,5],
      previewList: [
        "https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png"
      ],
      showImage: false,
      drawer: false,
      iconConfig: [
        {
          type: 1,
          color: "red",
          name: "激活状态"
        },
        {
          type: 1,
          color: "blue",
          name: "中继状态"
        },
        {
          type: 1,
          color: "yellow",
          name: "睡眠状态"
        },
        {
          type: 1,
          color: "black",
          name: "失效状态"
        },
        {
          type: 2,
          color: "green",
          name: "汇聚结点"
        },
        {
          type: 3,
          color: "purple",
          name: "可通信链路"
        },
        {
          type: 4,
          color: "",
          name: "重建区域"
        },
        {
          type: 5,
          color: "green",
          name: "重建点"
        }
      ]
    }
  },
  methods: {
    getImage() {
      var that = this;
      this.loading = true;
      API.getImage(this.param).then(
        function (res) {
          var base64 = res.data.images;
          var areas = res.data.areas;
          for (let index = 0; index < base64.length; index++) {
            const element = "data:image/png;base64," + base64[index];
            that.imageUrl.push(element);
            that.loading = false;
          }
          areas.unshift(0);
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
