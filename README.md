# 🤖 智能对话平台 - SpringAI

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-Latest-blue.svg)](https://spring.io/projects/spring-ai)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**一个集成了现代AI技术的企业级多功能对话系统**

基于SpringAI生态构建，不仅提供了流畅的AI对话体验，更集成了RAG、函数调用、多模态等高级特性，具备高度的可扩展性和生产就绪的架构设计。

---

## ✨ 核心特性

| 特性模块 | 功能描述 |
| :--- | :--- |
| **💬 智能对话** | 深度集成**Ollama**本地模型，保障数据安全；支持多轮对话与深度思考。 |
| **⚡ 流式响应** | 采用 **Spring WebFlux** 响应式编程，实现非阻塞的流式输出，用户体验如丝般顺滑。 |
| **🧠 记忆持久化** | 通过自定义 `ChatMemoryRepository` 实现对话历史的数据库持久化，支持连贯的上下文对话。 |
| **🛠️ 函数调用** | 基于 **`@Tool`** 注解轻松声明外部函数，大幅扩展模型能力边界，实现复杂逻辑与计算。 |
| **🌐 多模态交互** | 支持文本、图像等多种输入模态，使AI能够感知并回应更丰富的用户意图。 |
| **📚 RAG增强检索** | 内置PDF文档解析器，自动提取内容并存入 **PostgreSQL向量数据库**，实现基于知识的精准回答。 |
| **🎯 提示词工程** | 精心设计的系统级提示词，确保模型输出准确、简练且符合业务需求。 |
| **📝 切面集成** | 利用**环绕通知**统一处理日志记录、聊天持久化与向量检索，架构清晰，非侵入式。 |

---

## 🛠️ 技术架构

### 后端技术栈

- **核心框架：** Spring Boot 3.x, Spring AI
- **响应式编程：** Spring WebFlux
- **数据持久化：** PostgreSQL (向量扩展), MyBatis/Spring Data JPA
- **缓存：** Redis
- **AI模型：** Ollama (本地部署)
- **文档处理：** PDFBox / Tika
- **构建工具：** Maven

### 部署与运维

- **容器化：** Docker
- **反向代理：** Nginx

---

## 🚀 快速开始

### 环境准备

1. **JDK 17** 或更高版本
2. **Maven 3.6+**
3. **PostgreSQL** ( `pgvector` 扩展)
4. **Redis**
5. **Ollama** ( `qwen:7b`, `deepseek-r1:1.5b`)

### 配置步骤

1. **克隆项目**
   ```bash
   git clone 项目地址复制
   cd ollama-ai
   ```

2. **数据库初始化**
   - 在PostgreSQL中创建数据库。
   - 运行项目中的 `schema.sql` 初始化表结构（如果适用）。
   - 确保 `pgvector` 扩展已安装。

3. **应用配置**
   编辑 `application.yml` 文件，配置您的数据源、Redis连接以及Ollama基础URL。
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/your_database
       username: your_username
       password: your_password
     data:
       redis:
         host: localhost
         port: 6379
   spring:
     ai:
       ollama:
         base-url: http://localhost:11434
   ```

4. **运行项目**
   ```bash
   mvn spring-boot:run
   ```
   访问 `http://localhost:8080` 即可体验。

---

## 📁 项目结构

```
src/main/java/
│
├── controller/       # 控制层，处理Web请求
├── service/          # 业务逻辑层，核心对话、RAG等服务
├── repository/       # 数据访问层，包括ChatMemoryRepository
├── config/           # 配置类，如WebFlux、AI Bean等
├── entity/           # 实体类
├── tool/             # 函数调用（@Tool）工具类
└── aspect/           # 切面，用于日志、持久化等统一处理
resources/
│
├── application.yml   # 主配置文件
└── prompts/          # 提示词模板文件
```

---

## 🎯 功能演示

### 1. 基础文本与流式对话
- 与本地模型进行流畅、低延迟的纯文本交流。

### 2. 基于文档的QA（RAG）
- 上传一份PDF技术文档。
- 随后即可针对该文档内容进行精准提问，系统将从向量库中检索相关信息并生成答案。

### 3. 函数调用演示
- **用户：** “现在北京的天气怎么样？”
- **AI：** 识别意图 → 调用已注册的 `WeatherService@Tool` 方法 → 获取真实天气数据 → 生成回复给用户。

### 4. 多模态对话
- 支持上传图片并向AI提问关于图片内容的问题。

---

## 🔮 未来规划

- [ ] 支持更多向量数据库（Chroma, Milvus）
- [ ] 实现更复杂的Agent工作流
- [ ] 增加用户管理与对话会话隔离
- [ ] 提供可视化的知识库管理界面
- [ ] 集成更多开源大模型（如通义千问、DeepSeek）

---

## 🤝 贡献

我们欢迎任何形式的贡献！如果您有任何建议或发现了Bug，请随时提交 [Issue]([您的项目地址]/issues) 或 [Pull Request]([您的项目地址]/pulls)。

---

## 📄 许可证

本项目基于 [MIT License](LICENSE) 开源。

---

## 💌 致谢

感谢 [Spring AI](https://spring.io/projects/spring-ai) 项目提供的强大AI集成能力，以及所有开源社区的支持。

---

**如果您觉得这个项目有帮助，请给它一个 ⭐ Star！**

---

这份README可以直接用于您的GitHub仓库。它清晰地展示了项目的价值、技术深度和您的个人能力，能给访客（包括潜在的面试官）留下深刻的印象。
